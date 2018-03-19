package ch.mollusca.samples.reactive.fx;

import ch.mollusca.samples.reactive.presenters.ChatMessagesPresenter;
import ch.mollusca.samples.reactive.repositories.ChatRepository;
import ch.mollusca.samples.reactive.repositories.HelloRepository;
import ch.mollusca.samples.reactive.views.ChatMessagesView;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ClientMain extends Application implements ChatMessagesView {

    private final TextField queryText;
    private final TextField messageText;
    private final TextField nickName;
    private final GridPane bottomPane;
    private final Button helloButton;
    private final GridPane topPane;

    private final Retrofit retrofit;
    private final HelloRepository helloRepository;

    private final ChatMessagesPresenter chatMessagesPresenter;

    public ClientMain() {
        retrofit = createRetrofitClient("http://localhost:8080/");
        helloRepository = new HelloRepository(retrofit);

        chatMessagesPresenter = new ChatMessagesPresenter(new ChatRepository(retrofit), JavaFxScheduler.platform());
        chatMessagesPresenter.setView(this);
        chatMessagesPresenter.startFetchingMessages();

        queryText = new TextField();
        helloButton = new Button("Say hello...");
        topPane = new GridPane();
        wireTopComponents();

        messageText = new TextField();
        nickName = new TextField();
        bottomPane = new GridPane();
        wireBottomPaneComponents();
    }

    private void wireTopComponents() {
        topPane.setHgap(10);
        topPane.getColumnConstraints().addAll(
                new ColumnConstraints(100, 200, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true),
                new ColumnConstraints(80, 80, 200, Priority.SOMETIMES, HPos.RIGHT, false)
        );

        topPane.add(queryText, 0, 0);
        topPane.add(helloButton, 1, 0);
        JavaFxObservable.actionEventsOf(helloButton)
                .subscribe(actionEvent -> helloRepository
                        .hello("rx-java-fx")
                        .observeOn(JavaFxScheduler.platform())
                        .subscribe(saying -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Hello from server...");
                            alert.setHeaderText(null);
                            alert.setContentText(saying.getContent());

                            alert.showAndWait();
                        }));
    }

    private void wireBottomPaneComponents() {
        bottomPane.setHgap(10);
        bottomPane.getColumnConstraints().addAll(
                new ColumnConstraints(30, 30, 50),
                new ColumnConstraints(100, 200, Double.MAX_VALUE, Priority.SOMETIMES, HPos.LEFT, true),
                new ColumnConstraints(300, 300, Double.MAX_VALUE, Priority.ALWAYS, HPos.RIGHT, true)
        );
        bottomPane.add(new Label("nick:"), 0, 0);
        bottomPane.add(nickName, 1, 0);
        bottomPane.add(new Label("msg:"), 2, 0);
        bottomPane.add(messageText, 3, 0);
    }

    // TODO: replace manual dependency injection with guice.
    private Retrofit createRetrofitClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public void start(Stage stage) {
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("Search-As-You-Type with RxJava");

        Scene scene = new Scene(
                new BorderPane(null, topPane, null, bottomPane, null)
        );

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
