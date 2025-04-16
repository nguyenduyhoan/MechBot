package hoannd.mech.bot.MechBot.service.vocabularyservice;

import hoannd.mech.bot.MechBot.model.vocabularymodel.Vocabulary;
import hoannd.mech.bot.MechBot.repository.vocabularyrepository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VocabularyService {

    @Autowired
    private VocabularyRepository vocabularyRepository;

    // Lấy từ vựng theo chủ đề
    public List<Vocabulary> getVocabulariesByTopicId(Long topicId) {
        return vocabularyRepository.findByTopicId(topicId);
    }

    // Lấy từ vựng theo trình độ
    public List<Vocabulary> getVocabulariesByLevel(String level) {
        return vocabularyRepository.findByTopic_Level(level);
    }
}
