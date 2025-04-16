package hoannd.mech.bot.MechBot.repository.vocabularyrepository;

import hoannd.mech.bot.MechBot.model.vocabularymodel.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findByTopicId(Long topicId);

    List<Vocabulary> findByTopic_Level(String level);

}