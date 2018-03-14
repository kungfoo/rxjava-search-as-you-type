package ch.mollusca.samples.reactive.server.domain.math;

import io.reactivex.Observable;

import java.math.BigInteger;
import java.util.Iterator;

public class Fibonacci {
    /**
     * Provides an endless iterable of fibonacci numbers.
     */
    public static class FibonacciIterable implements Iterable<BigInteger> {
        @Override
        public Iterator<BigInteger> iterator() {
            return new Iterator<BigInteger>() {

                private BigInteger current = BigInteger.ONE;
                private BigInteger previous = BigInteger.ZERO;

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public BigInteger next() {
                    BigInteger result = current;
                    current = previous.add(current);
                    previous = result;
                    return result;
                }
            };
        }
    }

    /**
     * @return An endless Observable of fibonacci numbers.
     */
    public static Observable<BigInteger> sequence() {
        return Observable.fromIterable(new FibonacciIterable());
    }
}
