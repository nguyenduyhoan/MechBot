package hoannd.mech.bot.MechBot.controller.vocabularycontroller;

import hoannd.mech.bot.MechBot.model.vocabularymodel.Topic;
import hoannd.mech.bot.MechBot.service.vocabularyservice.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // Lấy danh sách chủ đề
    @GetMapping
    public List<Topic> getTopics() {
        return topicService.getAllTopics();
    }
}
