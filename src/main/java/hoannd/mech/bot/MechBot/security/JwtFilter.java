package hoannd.mech.bot.MechBot.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy JWT từ header Authorization
        String token = extractTokenFromHeader(request);

        if (token != null && jwtUtils.validateToken(token)) {
            // Lấy username từ JWT
            String username = jwtUtils.extractUsername(token);

            // Nếu token hợp lệ, tìm người dùng và tạo Authentication token
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Tạo Authentication token và đặt vào SecurityContext
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Tiếp tục với các bộ lọc khác trong chuỗi
        filterChain.doFilter(request, response);
    }

    // Phương thức để lấy JWT từ header Authorization
    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Trả về phần token sau "Bearer "
        }
        return null;
    }
}



