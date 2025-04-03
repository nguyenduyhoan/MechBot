package hoannd.mech.bot.MechBot.security;

import hoannd.mech.bot.MechBot.repository.UserRepository;
import hoannd.mech.bot.MechBot.model.User;
import hoannd.mech.bot.MechBot.model.Role;  // Đảm bảo rằng bạn có model Role

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Chuyển đổi danh sách roles thành danh sách authorities (SimpleGrantedAuthority)
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))  // Chuyển đổi tên role thành "ROLE_<name>"
                .collect(Collectors.toList());

        // Trả về UserDetails với các quyền
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())  // Mã hóa mật khẩu
                .authorities(authorities)  // Cung cấp danh sách authorities
                .build();
    }
}
