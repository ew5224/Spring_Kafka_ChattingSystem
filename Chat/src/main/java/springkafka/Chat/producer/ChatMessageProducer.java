package springkafka.Chat.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import springkafka.Chat.Domain.ChatMessage;

@Component
@Slf4j
public class ChatMessageProducer {
    @Autowired
    KafkaTemplate<Long, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private String topic ="chatsystem";


    public void sendChatMessage(ChatMessage chatMessage) throws JsonProcessingException {
        Long key = chatMessage.getMessageid();
        String value = objectMapper.writeValueAsString(chatMessage);

        ProducerRecord<Long, String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Long, String>> sendResultListenableFuture = kafkaTemplate.send(producerRecord);
        sendResultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);

            }

            @Override
            public void onSuccess(SendResult<Long, String> result) {
                handleSuccess(key,value,result);
            }
        });
    }

    private ProducerRecord<Long, String> buildProducerRecord(Long key, String value, String topic){
        return new ProducerRecord<Long, String>(topic, null, key, value, null);
    }

    private void handleFailure(Long key, String value, Throwable ex){
        try {
            log.error("Error occured on sending Message and the exception is {}", ex.getMessage());
            throw ex;
        }catch(Throwable throwable){
            log.error("Error in onFailure : {}",throwable.getMessage());
        }
    }

    private void handleSuccess(Long key, String value, SendResult<Long, String> result){
        log.info("Message Sent Successfully for the key : {} and the value is {}, partition is {}",key, value, result.getRecordMetadata().partition());
    }

}
