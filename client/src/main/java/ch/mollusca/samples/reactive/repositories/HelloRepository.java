package ch.mollusca.samples.reactive.repositories;

import ch.mollusca.samples.reactive.api.dtos.Saying;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.concurrent.TimeUnit;

public class HelloRepository {

    private interface HelloEndpoint {
        @GET("/hello/")
        Observable<Saying> sayHello(@Query("name") String name);
    }

    public HelloRepository(Retrofit retrofit) {
        this.helloEndpoint = retrofit.create(HelloEndpoint.class);
    }

    private final HelloEndpoint helloEndpoint;

    public Observable<Saying> hello(String name) {
        return helloEndpoint
                .sayHello(name)
                .subscribeOn(Schedulers.io())
                .delay(200, TimeUnit.MILLISECONDS);
    }

}