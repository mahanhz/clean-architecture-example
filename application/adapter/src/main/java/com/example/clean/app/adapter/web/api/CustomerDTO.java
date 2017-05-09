package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomerDTO {

    @JsonProperty("id")
    public final long id;

    @JsonProperty("name")
    public final NameDTO name;

    @JsonCreator
    public CustomerDTO(@JsonProperty("id") final long id,
                       @JsonProperty("name") final NameDTO name) {
        this.id = id;
        this.name = notNull(name);
    }
}
