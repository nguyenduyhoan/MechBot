package hoannd.mech.bot.MechBot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String username;
    private String newPassword;

    // Getters & Setters
}
