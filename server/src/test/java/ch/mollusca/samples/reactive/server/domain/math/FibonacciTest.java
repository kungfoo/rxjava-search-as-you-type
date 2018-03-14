package ch.mollusca.samples.reactive.server.domain.math;

import io.reactivex.Observable;
import org.junit.Test;

import java.math.BigInteger;

import static ch.mollusca.samples.reactive.testing.RxAssertions.subscribeAssertingThat;


public class FibonacciTest {
    @Test
    public void itShouldYieldTheFirst5NumbersCorrectly() {
        Observable<BigInteger> result = Fibonacci.sequence().take(5);

        subscribeAssertingThat(result)
                .emits(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(2),
                        BigInteger.valueOf(3),
                        BigInteger.valueOf(5)
                );
    }
}