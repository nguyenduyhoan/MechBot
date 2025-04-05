package hoannd.mech.bot.MechBot.controller;

import hoannd.mech.bot.MechBot.dto.LoginRequest;
import hoannd.mech.bot.MechBot.dto.RegisterRequest;
import hoannd.mech.bot.MechBot.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Map<String, String> tokens = authService.login(request); // 👈 gọi hàm login ở service
            return ResponseEntity.ok(tokens);          // ✅ trả về token (hoặc thông báo)
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
