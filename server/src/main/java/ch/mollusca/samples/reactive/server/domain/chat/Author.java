package ch.mollusca.samples.reactive.server.domain.chat;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
    private final String nickName;

    public Author(@Nonnull String nickName) {
        this.nickName = Objects.requireNonNull(nickName, "The nickname can never be null!");
    }

    @Nonnull
    public String getNickName() {
        return nickName;
    }
}
