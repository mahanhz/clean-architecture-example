package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomersV2DTO {

    @JsonProperty("customers")
    public final List<CustomerV2DTO> customers;

    public CustomersV2DTO(final List<CustomerV2DTO> customers) {
        this.customers = notNull(customers);
    }
}
