package hoannd.mech.bot.MechBot.service;

import hoannd.mech.bot.MechBot.dto.LoginRequest;
import hoannd.mech.bot.MechBot.dto.RegisterRequest;
import hoannd.mech.bot.MechBot.model.Role;
import hoannd.mech.bot.MechBot.model.RoleName;
import hoannd.mech.bot.MechBot.model.User;
import hoannd.mech.bot.MechBot.repository.RoleRepository;
import hoannd.mech.bot.MechBot.repository.UserRepository;
import hoannd.mech.bot.MechBot.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; // 👈 thêm dòng này

    @Autowired
    private AuthenticationManager authManager;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) { // 👈 truyền vào constructor
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String registerUser(RegisterRequest request) {
        // Kiểm tra xem tên người dùng đã tồn tại chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Tạo người dùng mới
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Tìm vai trò mặc định, nếu không tìm thấy thì tạo mới
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseGet(() -> {
                    // Nếu vai trò chưa tồn tại, tạo mới vai trò mặc định và lưu vào cơ sở dữ liệu
                    Role newRole = new Role();
                    newRole.setName(RoleName.ROLE_USER);
                    roleRepository.save(newRole);
                    return newRole;
                });

        // Gán vai trò cho người dùng
        user.setRoles(Collections.singleton(userRole));

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String, String> login(LoginRequest request) {
        try {
            // Xác thực thông tin người dùng
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails user = (UserDetails) auth.getPrincipal();

            // Tạo access token và refresh token
            String accessToken = jwtUtils.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());

            // Trả về token dưới dạng map
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
