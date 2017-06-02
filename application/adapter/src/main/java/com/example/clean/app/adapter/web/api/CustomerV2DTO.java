package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerV2DTO {

    @JsonProperty("id")
    public final long id;

    @JsonProperty("name")
    public final NameV2DTO name;

    @JsonCreator
    public CustomerV2DTO(@JsonProperty("id") final long id,
                         @JsonProperty("name") final NameV2DTO name) {
        this.id = id;
        this.name = notNull(name);
    }
}
