package hoannd.mech.bot.MechBot.service;

import hoannd.mech.bot.MechBot.dto.RegisterRequest;
import hoannd.mech.bot.MechBot.model.Role;
import hoannd.mech.bot.MechBot.model.RoleName;
import hoannd.mech.bot.MechBot.model.User;
import hoannd.mech.bot.MechBot.repository.RoleRepository;
import hoannd.mech.bot.MechBot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
}
