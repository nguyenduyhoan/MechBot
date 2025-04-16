package hoannd.mech.bot.MechBot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocabResponse {
    private String word;
    private String phonetic;
    private String definition;

    // Constructor
    public VocabResponse(String word, String phonetic, String definition) {
        this.word = word;
        this.phonetic = phonetic;
        this.definition = definition;
    }

    // Getter & Setter
    // (có thể dùng Lombok nếu thích)
}

