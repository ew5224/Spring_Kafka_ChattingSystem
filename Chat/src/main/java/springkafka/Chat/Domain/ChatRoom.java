package springkafka.Chat.Domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import springkafka.Chat.Service.ChatService;

import java.net.http.WebSocket;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
        if(chatMessage.getChatMessageType().equals(ChatMessageType.NEW)){
            sessions.add(session);
            chatMessage.setContent(chatMessage.getSender().getName()+"님이 입장했습니다.");
        }sendMessage(chatMessage,chatService);

    }
    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }


}
