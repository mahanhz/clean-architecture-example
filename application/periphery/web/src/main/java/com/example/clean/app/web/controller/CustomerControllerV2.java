package com.example.clean.app.web.controller;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.adapter.web.api.CustomerV2DTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.example.clean.app.adapter.web.api.CustomersV2DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.clean.app.web.controller.CommonLinks.*;
import static com.example.clean.app.web.controller.MediaTypes.APPLICATION_JSON_V2_VALUE;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/customers", produces = APPLICATION_JSON_V2_VALUE)
public class CustomerControllerV2 {

    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    private CustomerAdapter customerAdapter;

    @Autowired
    public CustomerControllerV2(final CustomerAdapter customerAdapter) {
        this.customerAdapter = notNull(customerAdapter);
    }

    @GetMapping
    public ResponseEntity<Resource<CustomersV2DTO>> customers() {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerControllerV2.class).customers());

        final ControllerLinkBuilder createLink = linkTo(CustomerControllerV2.class);

        final Resource<CustomersV2DTO> customersDto = new Resource<>(customerAdapter.customersV2());
        customersDto.add(selfLink.withSelfRel());
        customersDto.add(homeLink());
        customersDto.add(createLink.withRel(REL_CREATE));
        customersDto.add(customerLinks());

        return ResponseEntity.ok(customersDto);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<Resource<CustomerV2DTO>> customer(@PathVariable final long customerId) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerControllerV2.class).customer(customerId));

        final ControllerLinkBuilder updateLink = linkTo(CustomerControllerV2.class).slash(customerId);
        final ControllerLinkBuilder deleteLink = linkTo(CustomerControllerV2.class).slash(customerId);

        final Resource<CustomerV2DTO> customerDto = new Resource<>(customerAdapter.customerV2(customerId));
        customerDto.add(selfLink.withSelfRel());
        customerDto.add(homeLink());
        customerDto.add(customersLink());
        customerDto.add(updateLink.withRel(REL_UPDATE));
        customerDto.add(deleteLink.withRel(REL_DELETE));

        return ResponseEntity.ok(customerDto);
    }

    private List<Link> customerLinks() {
        final CustomersDTO customers = customerAdapter.customers();

        return customers.customers.stream()
                                  .map(cust -> linkTo(methodOn(CustomerControllerV2.class).customer(cust.id)).withRel(REL_CUSTOMER_PREFIX + cust.id))
                                  .collect(Collectors.toList());
    }
}
