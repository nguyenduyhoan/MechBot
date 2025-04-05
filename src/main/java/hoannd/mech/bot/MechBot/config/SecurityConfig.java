package hoannd.mech.bot.MechBot.config;

import hoannd.mech.bot.MechBot.security.CustomAuthenticationProvider;
import hoannd.mech.bot.MechBot.security.JwtAuthenticationEntryPoint;
import hoannd.mech.bot.MechBot.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private UserDetailsService userDetailsService;  // Đảm bảo rằng bạn đã khai báo UserDetailsService

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;  // Tiêm CustomAuthenticationProvider

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Đảm bảo AuthenticationManager được khai báo
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // Cho phép truy cập API xác thực
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Chỉ ADMIN mới có quyền truy cập
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // USER & ADMIN đều có quyền
                        .anyRequest().authenticated()  // Các yêu cầu khác đều phải xác thực
                )
                .authenticationProvider(customAuthenticationProvider)  // Sử dụng CustomAuthenticationProvider để xác thực
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)  // Thêm JwtFilter để kiểm tra token trong yêu cầu
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));  // Sử dụng JwtAuthenticationEntryPoint để xử lý lỗi xác thực

        return http.build();
    }
}





