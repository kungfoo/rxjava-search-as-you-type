package ch.mollusca.samples.reactive.api.dtos.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageView {
    private String nickName;
    private String content;
    private LocalDateTime messageReceivedTime;
}
