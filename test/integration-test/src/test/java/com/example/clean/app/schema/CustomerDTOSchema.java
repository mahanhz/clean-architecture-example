package com.example.clean.app.schema;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

final class CustomerDTOSchema extends ResourceSupport {

    @JsonUnwrapped
    private CustomerDTO customersDTO;
}
