package ch.mollusca.samples.reactive.api.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Saying {
    private long id;

    @NonNull
    private String content;
}
