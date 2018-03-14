package ch.mollusca.samples.reactive.api.dtos.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MessageView {
    @JsonProperty
    String getContent();

    @JsonProperty
    String getAuthorNickName();
}
