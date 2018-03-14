package ch.mollusca.samples.reactive.server.domain.chat;

import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable, MessageView {

    private final Author author;
    private final String content;
    private final LocalDateTime messageTime;

    public Message(@Nonnull Author author, @Nonnull String content, @Nonnull LocalDateTime messageTime) {
        this.author = author;
        this.content = content;
        this.messageTime = messageTime;
    }


    @Nonnull
    public String getContent() {
        return content;
    }

    @Nonnull
    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    @Nonnull
    public String getAuthorNickName() {
        return author.getNickName();
    }
}
