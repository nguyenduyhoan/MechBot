package hoannd.mech.bot.MechBot.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final String JWT_SECRET = "yourSecretKey";
    private static final long JWT_EXPIRATION = 15 * 60 * 1000;  // 15 minutes
    private static final long JWT_REFRESH_EXPIRATION = 7 * 24 * 60 * 60 * 1000;  // 7 days

    // Tạo token JWT
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Tạo Refresh Token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Trích xuất username từ token
    public String extractUsername(String token) {
        // Sử dụng Jwts.parser() thay cho parser().build() trong phiên bản 0.9.1
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Kiểm tra token hợp lệ
    public boolean validateToken(String token) {
        try {
            // Sử dụng Jwts.parser() thay cho parser().build() trong phiên bản 0.9.1
            Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
