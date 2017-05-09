package com.example.clean.app.web.controller;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.example.clean.app.web.controller.CommonLinks.customersLink;
import static com.example.clean.app.web.controller.CommonLinks.homeLink;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/customers")
public class CustomerController {

    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    private CustomerAdapter customerAdapter;

    @Autowired
    public CustomerController(final CustomerAdapter customerAdapter) {
        this.customerAdapter = notNull(customerAdapter);
    }

    @GetMapping
    public ResponseEntity<Resource<CustomersDTO>> customers() {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerController.class).customers());

        final ControllerLinkBuilder createGroupLink = linkTo(CustomerController.class).slash(CREATE);

        final Resource<CustomersDTO> customersDto = new Resource<>(customerAdapter.customers());
        customersDto.add(selfLink.withSelfRel());
        customersDto.add(homeLink());
        customersDto.add(createGroupLink.withRel("create"));

        return ResponseEntity.ok(customersDto);
    }

    @GetMapping(path = "{customerId}")
    public ResponseEntity<Resource<CustomerDTO>> customer(@PathVariable final long customerId) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerController.class).customer(customerId));

        final ControllerLinkBuilder updateLink = linkTo(CustomerController.class).slash(UPDATE);
        final ControllerLinkBuilder deleteLink = linkTo(CustomerController.class).slash(customerId);


        final Resource<CustomerDTO> customerDto = new Resource<>(customerAdapter.customer(customerId));
        customerDto.add(selfLink.withSelfRel());
        customerDto.add(homeLink());
        customerDto.add(updateLink.withRel("update"));
        customerDto.add(deleteLink.withRel("delete"));

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping(path = CREATE)
    public ResponseEntity<?> create(@RequestBody @Valid final CustomerDTO customerDto) {

        customerAdapter.create(customerDto);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    @PutMapping(path = UPDATE)
    public ResponseEntity<?> update(@RequestBody @Valid final CustomerDTO customerDto) {

        customerAdapter.update(customerDto);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<?> delete(@PathVariable final long customerId) {

        customerAdapter.delete(customerId);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }
}
