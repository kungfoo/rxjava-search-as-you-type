package ch.mollusca.samples.reactive.server.repositories;

import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;

import java.util.List;

public interface ChatRepository {
    void addMessage(String nickName, String content);
    List<MessageView> getTenMostRecentMessages();
    List<MessageView> getMostRecentMessages(int count);
}
