package springkafka.Chat.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springkafka.Chat.Domain.ChatMessage;
import springkafka.Chat.Domain.ChatMessageType;
import springkafka.Chat.producer.ChatMessageProducer;

@RestController
@Slf4j
public class ChatMessageController {

    @Autowired
    ChatMessageProducer chatMessageProducer;

    @PostMapping("/chatmessage")
    public ResponseEntity<ChatMessage> postChatMessage(@RequestBody ChatMessage chatMessage) throws JsonProcessingException {
        chatMessage.setChatMessageType(ChatMessageType.NEW);
        log.info("Sending Start");
        chatMessageProducer.sendChatMessage(chatMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatMessage);
    }
}
