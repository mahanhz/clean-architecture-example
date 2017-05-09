package com.example.clean.app.web.controller;

import com.example.clean.app.adapter.web.CustomerAdapter;
import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.clean.app.web.controller.CommonLinks.customersLink;
import static com.example.clean.app.web.controller.CommonLinks.homeLink;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<CustomersDTO>> customers() {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerController.class).customers());

        final ControllerLinkBuilder createLink = linkTo(CustomerController.class).slash(CREATE);

        final Resource<CustomersDTO> customersDto = new Resource<>(customerAdapter.customers());
        customersDto.add(selfLink.withSelfRel());
        customersDto.add(homeLink());
        customersDto.add(createLink.withRel("create"));
        customersDto.add(customerLinks());

        return ResponseEntity.ok(customersDto);
    }

    @GetMapping(path = "/{customerId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource<CustomerDTO>> customer(@PathVariable final long customerId) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerController.class).customer(customerId));

        final ControllerLinkBuilder updateLink = linkTo(CustomerController.class).slash(UPDATE);
        final ControllerLinkBuilder deleteLink = linkTo(CustomerController.class).slash(customerId);


        final Resource<CustomerDTO> customerDto = new Resource<>(customerAdapter.customer(customerId));
        customerDto.add(selfLink.withSelfRel());
        customerDto.add(homeLink());
        customerDto.add(customersLink());
        customerDto.add(updateLink.withRel("update"));
        customerDto.add(deleteLink.withRel("delete"));

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping(path = "/" + CREATE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid final CustomerDTO customerDto) {

        customerAdapter.create(customerDto);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    @PutMapping(path = "/" + UPDATE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid final CustomerDTO customerDto) {

        customerAdapter.update(customerDto);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<?> delete(@PathVariable final long customerId) {

        customerAdapter.delete(customerId);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    private List<Link> customerLinks() {
        final CustomersDTO customers = customerAdapter.customers();

        return customers.customers.stream()
                      .map(cust -> linkTo(methodOn(CustomerController.class).customer(cust.id)).withRel("customer-" + cust.id))
                      .collect(Collectors.toList());
    }
}
