package ch.mollusca.samples.reactive.fx;

import ch.mollusca.samples.reactive.repositories.HelloRepository;
import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class ClientMain extends Application {

    private final TextField queryText;
    private final Label enteredText;

    private final Retrofit retrofit;
    private final HelloRepository helloRepository;

    public ClientMain() {
        retrofit = createRetrofitClient("http://localhost:8080/");
        helloRepository = new HelloRepository(retrofit);

        helloRepository.hello("rx-java-fx")
                .observeOn(JavaFxScheduler.platform())
                .subscribe(saying -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Hello from server...");
                    alert.setHeaderText(null);
                    alert.setContentText(saying.getContent());

                    alert.showAndWait();
                });

        queryText = new TextField();
        enteredText = new Label();

        Observable<String> reverseTexts = JavaFxObservable
                .valuesOf(queryText.textProperty())
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .map(query -> {
                    StringBuilder reverseText = new StringBuilder(query).reverse();
                    LocalTime now = LocalTime.now();
                    return String.format("Query at %s: (reverse): %s", now, reverseText);
                })
                .subscribeOn(Schedulers.computation());

        reverseTexts
                .observeOn(JavaFxScheduler.platform())
                .subscribe(enteredText::setText);
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
    public void start(Stage stage) throws Exception {
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setTitle("Search-As-You-Type with RxJava");

        Scene scene = new Scene(
                new BorderPane(null, queryText, null, enteredText, null)
        );

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
