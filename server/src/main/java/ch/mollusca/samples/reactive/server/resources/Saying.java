package ch.mollusca.samples.reactive.server.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {
    private final long id;
    private final String content;

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
