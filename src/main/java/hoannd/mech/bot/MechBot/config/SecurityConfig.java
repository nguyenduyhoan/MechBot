package hoannd.mech.bot.MechBot.config;

import hoannd.mech.bot.MechBot.security.CustomAuthenticationProvider;
import hoannd.mech.bot.MechBot.security.JwtAuthenticationEntryPoint;
import hoannd.mech.bot.MechBot.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // Vô hiệu hóa CSRF (không cần với API)
                .cors(cors -> {})  // Cấu hình CORS (nếu cần)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Cho phép truy cập API xác thực
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Chỉ ADMIN mới có quyền truy cập
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // USER & ADMIN đều có quyền
                        .anyRequest().authenticated()  // Các yêu cầu khác đều phải xác thực
                )
                .authenticationProvider(customAuthenticationProvider)  // Sử dụng CustomAuthenticationProvider để xác thực
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // Thêm JwtFilter để kiểm tra token trong yêu cầu
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))  // Sử dụng JwtAuthenticationEntryPoint để xử lý lỗi xác thực
                .build();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Mã hóa mật khẩu bằng BCrypt
//    }
}




