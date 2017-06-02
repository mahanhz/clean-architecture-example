package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class NameV2DTO {

    @JsonProperty("fullName")
    public final String fullName;

    @JsonCreator
    public NameV2DTO(@JsonProperty("fullName") final String fullName) {
        this.fullName = notBlank(fullName, "Full name is required");
    }
}
