package springkafka.Chat.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springkafka.Chat.Domain.Member;
import springkafka.Chat.producer.ChatMessageProducer;

@Service
public class MemberService {

    private final ChatMessageProducer chatMessageProducer;

    public MemberService(ChatMessageProducer chatMessageProducer){
        this.chatMessageProducer = chatMessageProducer;
    }

    public void sendMessage(){

    }

    public void join(){

    }
    public void quit(){

    }


}
