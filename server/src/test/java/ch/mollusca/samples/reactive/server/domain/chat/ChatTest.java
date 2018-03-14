package ch.mollusca.samples.reactive.server.domain.chat;

import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatTest {

    private Chat chat;

    @Before
    public void setup() {
        chat = new Chat();
    }

    @Test
    public void itShouldEnableAddingMessages() {
        chat.addMessage("Foo", "Can has messages", LocalDateTime.now());

        assertThat(chat.getTenMostRecentMessages().get(0).getContent(), containsString("Can has messages"));
    }

    @Test
    public void itShouldReturnTheLastNMessagesProperly() {
        for(int i = 0; i < 100; i++) {
            chat.addMessage("Foo", "Message #"+i, LocalDateTime.now());
        }

        List<Message> tenRecentMessages = chat.getMostRecentMessages(10);
        assertThat(tenRecentMessages.size(), is(10));
        assertThat(tenRecentMessages.get(9).getContent(), containsString("#99"));
    }
}