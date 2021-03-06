package ch.mollusca.samples.reactive.server.repositories;

import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;
import ch.mollusca.samples.reactive.server.domain.chat.Chat;
import ch.mollusca.samples.reactive.server.domain.chat.Message;
import pl.setblack.airomem.core.PersistenceController;
import pl.setblack.airomem.core.builders.PrevaylerBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultChatRepository implements  ChatRepository{

    public static final String DEFAULT_CHAT_STORAGE = "./chats";

    private final PersistenceController<Chat> controller;

    public DefaultChatRepository() {
        Path chatStorageFolder = Paths.get(DEFAULT_CHAT_STORAGE);

        controller = PrevaylerBuilder.<Chat>newBuilder()
                .withFolder(chatStorageFolder)
                .disableRoyalFoodTester()
                .useSupplier(Chat::new)
                .build();
    }

    @Override
    public void addMessage(String nickName, String content) {
        controller.execute((chat, context) -> {
            chat.addMessage(nickName, content, LocalDateTime.ofInstant(context.time, ZoneId.systemDefault()));
        });
    }

    @Override
    public List<MessageView> getTenMostRecentMessages() {
        return controller.query(Chat::getTenMostRecentMessages)
                .stream()
                .map(projectToMessageView())
                .collect(Collectors.toList());
    }

    private Function<Message, MessageView> projectToMessageView() {
        return msg -> new MessageView(msg.getAuthorNickName(), msg.getContent(), msg.getMessageTime());
    }

    @Override
    public List<MessageView> getMostRecentMessages(int count) {
        return controller.query(chat -> chat.getMostRecentMessages(count))
                .stream()
                .map(projectToMessageView())
                .collect(Collectors.toList());
    }

}
