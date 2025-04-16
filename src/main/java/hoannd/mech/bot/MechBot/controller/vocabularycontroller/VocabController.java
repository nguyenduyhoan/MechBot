package hoannd.mech.bot.MechBot.controller.vocabularycontroller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hoannd.mech.bot.MechBot.dto.VocabResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/vocab", produces = MediaType.APPLICATION_JSON_VALUE)
public class VocabController {

    @GetMapping("/{word}")
    public ResponseEntity<?> getWord(@PathVariable String word) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
        RestTemplate restTemplate = new RestTemplate();

        try {
            String jsonResult = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResult);

            JsonNode firstEntry = root.get(0);
            String phonetic = firstEntry.path("phonetic").asText("");
            String definition = firstEntry.path("meanings").get(0)
                    .path("definitions").get(0)
                    .path("definition").asText("");

            VocabResponse response = new VocabResponse(word, phonetic, definition);

            return ResponseEntity.ok(response);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Từ '" + word + "' không tồn tại trong từ điển.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}

