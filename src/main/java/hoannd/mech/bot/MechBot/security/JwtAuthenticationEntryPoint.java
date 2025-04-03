package hoannd.mech.bot.MechBot.security;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Thiết lập mã trạng thái lỗi HTTP là Unauthorized (401)
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is either missing or invalid");
    }
}
