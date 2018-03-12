package ch.mollusca.samples.reactive.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FibonacciSequence {

    public static FibonacciSequence EMPTY = new FibonacciSequence(ImmutableList.of());

    @NonNull
    private List<BigInteger> numbers;

    @JsonProperty
    public int getN() {
        return numbers.size();
    }

    @JsonProperty
    public List<String> getNumbers() {
        return numbers
                .stream()
                .map(BigInteger::toString)
                .collect(Collectors.toList());
    }

}
