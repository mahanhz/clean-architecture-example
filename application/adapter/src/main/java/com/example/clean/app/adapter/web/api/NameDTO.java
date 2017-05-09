package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.notBlank;

public class NameDTO {

    @JsonProperty("firstName")
    public final String firstName;
    @JsonProperty("middleName")
    public final String middleName;
    @JsonProperty("lastName")
    public final String lastName;
    @JsonProperty("suffix")
    public final String suffix;

    @JsonCreator
    public NameDTO(@JsonProperty("firstName") final String firstName,
                   @JsonProperty("middleName") final String middleName,
                   @JsonProperty("lastName") final String lastName,
                   @JsonProperty("suffix") final String suffix) {
        this.firstName = notBlank(firstName, "First name is required");
        this.middleName = middleName;
        this.lastName = notBlank(lastName, "Last name is required");
        this.suffix = suffix;
    }
}
