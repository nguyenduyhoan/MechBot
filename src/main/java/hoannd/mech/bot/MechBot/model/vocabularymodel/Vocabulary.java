package hoannd.mech.bot.MechBot.model.vocabularymodel;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vocabulary")
@Getter
@Setter
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Topic topic;  // Mối quan hệ với chủ đề

    private String word;
    private String meaning;
    private String pronunciation;
    private String imageUrl;
    private String exampleSentence;
}

