package ch.mollusca.samples.reactive.testing;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import junit.framework.AssertionFailedError;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.junit.MatcherAssert.assertThat;

public class RxAssertions {

    public static <T> ObservableAssertions<T> subscribeAssertingThat(Observable<T> observable) {
        return new ObservableAssertions<>(observable);
    }

    public static class ObservableAssertions<T> {

        private List<T> result;
        private Throwable error;
        private boolean completed;
        private boolean subscribed;

        public ObservableAssertions(Observable<T> observable) {
            completed = false;
            subscribed = false;
            result = new ArrayList<>();

            observable.subscribeOn(Schedulers.trampoline())
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onComplete() {
                            completed = true;
                        }

                        @Override
                        public void onError(Throwable error) {
                            ObservableAssertions.this.error = error;
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                            subscribed = true;
                        }

                        @Override
                        public void onNext(T item) {
                            result.add(item);
                        }
                    });
        }

        public ObservableAssertions<T> completesSuccessfully() {
            if (!completed || error != null) {
                if (error != null) error.printStackTrace();
                throw new AssertionFailedError("Observable has not completed successfully - cause: "
                        + (error != null ? error : "onComplete not called"));
            }
            return this;
        }

        public ObservableAssertions<T> wasSubscribed() {
            if(!subscribed) {
                throw new AssertionFailedError("Observable was never subscribed!");
            }
            return this;
        }

        public ObservableAssertions<T> fails() {
            if (error == null) {
                throw new AssertionFailedError("Observable has not failed!");
            }
            return this;
        }

        public ObservableAssertions<T> failsWithError(Throwable throwable) {
            fails();
            if (!throwable.equals(error)) {
                throw new AssertionFailedError("Observable has failed with a different error," +
                        " expected is " + throwable + " but thrown was " + error);
            }
            return this;
        }

        public ObservableAssertions<T> hasSize(int numItemsExpected) {
            if (numItemsExpected != result.size()) {
                throw new AssertionFailedError("Observable has emitted " + result.size()
                        + " items but expected was " + numItemsExpected);
            }
            return this;
        }

        @SafeVarargs
        public final ObservableAssertions<T> emits(T... itemsExpected) {
            completesSuccessfully();
            assertEmittedEquals(itemsExpected);
            return this;
        }

        @SuppressWarnings("unchecked")
        public ObservableAssertions<T> emits(Collection<T> itemsExpected) {
            completesSuccessfully();
            assertEmittedEquals((T[]) itemsExpected.toArray());
            return this;
        }

        @SafeVarargs
        public final ObservableAssertions<T> emitsMatching(TypeSafeMatcher<T>... itemMatchers) {
            completesSuccessfully();
            assertEmittedItemsMatch(itemMatchers);
            return this;
        }

        private void assertEmittedItemsMatch(TypeSafeMatcher<T>[] itemMatchers) {
            hasSize(itemMatchers.length);
            for (int i = 0; i < itemMatchers.length; i++) {
                assertThat(result.get(i), itemMatchers[i]);
            }
        }

        public ObservableAssertions<T> emitsNothing() {
            completesSuccessfully();
            if (result.size() > 0) {
                throw new AssertionFailedError("Observable has emitted " + result.size() + " items");
            }
            return this;
        }

        private void assertEmittedEquals(T[] itemsExpected) {
            hasSize(itemsExpected.length);
            for (int i = 0; i < itemsExpected.length; i++) {
                T expected = itemsExpected[i];
                T actual = result.get(i);
                if (!expected.equals(actual)) {
                    throw new AssertionFailedError("Emitted item in position " + i + " does not match," +
                            "  expected " + expected + " actual " + actual);
                }
            }
        }


    }
}
