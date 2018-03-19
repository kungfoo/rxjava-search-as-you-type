package ch.mollusca.samples.reactive.repositories;

import ch.mollusca.samples.reactive.api.dtos.chat.IncomingMessage;
import ch.mollusca.samples.reactive.api.dtos.chat.MessageView;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;


public class ChatRepository {

    private interface ChatEndpoint {
        @GET("/chat")
        Observable<List<MessageView>> lastMessages(@Query("count") int count);

        @POST("/chat")
        void sendMessage(IncomingMessage message);
    }

    private final ChatEndpoint chatEndpoint;

    public ChatRepository(Retrofit retrofit) {
        this.chatEndpoint = retrofit.create(ChatEndpoint.class);
    }

    public Observable<List<MessageView>> lastMessages(int count) {
        return chatEndpoint
                .lastMessages(count)
                .subscribeOn(Schedulers.io());
    }
}
