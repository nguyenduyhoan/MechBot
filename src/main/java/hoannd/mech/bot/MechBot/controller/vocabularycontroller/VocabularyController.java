package hoannd.mech.bot.MechBot.controller.vocabularycontroller;

import hoannd.mech.bot.MechBot.model.vocabularymodel.Vocabulary;
import hoannd.mech.bot.MechBot.service.vocabularyservice.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocab")
public class VocabularyController {

    @Autowired
    private VocabularyService vocabularyService;

    // Lấy từ vựng theo chủ đề
    @GetMapping("/topic/{topicId}")
    public List<Vocabulary> getVocabulariesByTopic(@PathVariable Long topicId) {
        return vocabularyService.getVocabulariesByTopicId(topicId);
    }

    // Lấy từ vựng theo trình độ
    @GetMapping("/level/{level}")
    public List<Vocabulary> getVocabulariesByLevel(@PathVariable String level) {
        return vocabularyService.getVocabulariesByLevel(level);
    }
}