package ch.mollusca.samples.fx;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class ClientMain extends Application {

    private final TextField queryText;
    private final Label enteredText;


    public ClientMain() {
        queryText = new TextField();
        enteredText = new Label();

        Observable<String> reverseTexts = JavaFxObservable
                .valuesOf(queryText.textProperty())
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .map(query -> "Query submitted (reverse): " + new StringBuilder(query).reverse())
                .subscribeOn(Schedulers.computation());

        reverseTexts
                .observeOn(JavaFxScheduler.platform())
                .subscribe(enteredText::setText);
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
