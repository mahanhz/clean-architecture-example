package com.example.clean.app.schema;

import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

final class CustomersDTOSchema extends ResourceSupport {

    @JsonUnwrapped
    private CustomersDTO customersDTO;
}
