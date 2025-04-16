package hoannd.mech.bot.MechBot.service.vocabularyservice;

import hoannd.mech.bot.MechBot.model.vocabularymodel.Topic;
import hoannd.mech.bot.MechBot.repository.vocabularyrepository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    // Lấy danh sách chủ đề
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
