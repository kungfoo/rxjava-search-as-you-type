package ch.mollusca.samples.reactive.api.dtos.chat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IncomingMessage {
    private String content;
    private String nickName;
}
