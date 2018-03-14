package ch.mollusca.samples.reactive.server.domain.chat;

import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;
import pl.setblack.airomem.core.WriteChecker;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Chat implements Serializable {

    private final CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<Message>();

    public void addMessage(@Nonnull String nickName, @Nonnull String content, @Nonnull LocalDateTime messageTime) {
        WriteChecker.hasPrevalanceContext();
        Author author = new Author(nickName);
        Message message = new Message(author, content, messageTime);
        messages.add(message);
    }

    public List<MessageView> getTenMostRecentMessages() {
        return getMostRecentMessages(10);
    }

    @Nonnull
    public List<MessageView> getMostRecentMessages(int count) {
        return messages.stream()
                .skip(Math.max(messages.size() - count, 0))
                .limit(count)
                .collect(Collectors.toList());
    }
}
