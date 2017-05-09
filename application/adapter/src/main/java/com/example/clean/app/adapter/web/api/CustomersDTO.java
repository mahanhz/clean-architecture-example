package com.example.clean.app.adapter.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class CustomersDTO {

    @JsonProperty("customers")
    public final List<CustomerDTO> customers;

    public CustomersDTO(final List<CustomerDTO> customers) {
        this.customers = notNull(customers);
    }
}
