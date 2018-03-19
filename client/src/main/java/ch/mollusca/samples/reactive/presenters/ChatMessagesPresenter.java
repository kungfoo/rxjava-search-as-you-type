package ch.mollusca.samples.reactive.presenters;

import ch.mollusca.samples.reactive.repositories.ChatRepository;
import ch.mollusca.samples.reactive.views.ChatMessagesView;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class ChatMessagesPresenter {

    private ChatMessagesView view;

    private final ChatRepository repository;
    private final Scheduler uiScheduler;

    public ChatMessagesPresenter(@Nonnull ChatRepository repository, @Nonnull Scheduler uiScheduler) {
        this.repository = requireNonNull(repository);
        this.uiScheduler = requireNonNull(uiScheduler);
    }

    public void setView(ChatMessagesView view) {
        this.view = requireNonNull(view, "View can never be null!");
    }

    public void startFetchingMessages() {
        Observable.timer(400, TimeUnit.MILLISECONDS)
                .subscribe(num -> {
                    repository
                            .lastMessages(10)
                            .observeOn(uiScheduler)
                            // TODO: update view here.
                            .subscribe(System.out::println);
                });
    }
}
