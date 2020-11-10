package springkafka.Chat.Domain;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
public class ChatMessage {
    private Long messageid;
    private Member sender;
    private String content;
    private ChatMessageType chatMessageType;
    private String roomId;

}
