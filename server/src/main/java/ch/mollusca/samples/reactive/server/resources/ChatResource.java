package ch.mollusca.samples.reactive.server.resources;

import ch.mollusca.samples.reactive.api.dtos.chat.IncomingMessage;
import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;
import ch.mollusca.samples.reactive.server.repositories.ChatRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/chat")
public class ChatResource {

    private final ChatRepository chatRepository;

    public ChatResource(ChatRepository chatRepository) {
        this.chatRepository = Objects.requireNonNull(chatRepository, "Chat repo can never be null!");
    }

    @GET
    public List<MessageView> getMessages(@QueryParam("count") Optional<Integer> count) {
        return chatRepository.getMostRecentMessages(count.orElse(10));
    }

    @PUT
    public void createMessage(IncomingMessage msg) {
        chatRepository.addMessage(msg.getNickName(), msg.getContent());
    }
}
