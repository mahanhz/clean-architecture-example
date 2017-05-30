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

import static com.example.clean.app.web.controller.CommonLinks.*;
import static com.example.clean.app.web.controller.MediaTypes.APPLICATION_JSON_V1_VALUE;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/customers", produces = APPLICATION_JSON_V1_VALUE)
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

        final ControllerLinkBuilder createLink = linkTo(CustomerController.class).slash(CREATE);

        final Resource<CustomersDTO> customersDto = new Resource<>(customerAdapter.customers());
        customersDto.add(selfLink.withSelfRel());
        customersDto.add(homeLink());
        customersDto.add(createLink.withRel(REL_CREATE));
        customersDto.add(customerLinks());

        return ResponseEntity.ok(customersDto);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<Resource<CustomerDTO>> customer(@PathVariable final long customerId) {

        final ControllerLinkBuilder selfLink = linkTo(methodOn(CustomerController.class).customer(customerId));

        final ControllerLinkBuilder updateLink = linkTo(CustomerController.class).slash(customerId);
        final ControllerLinkBuilder deleteLink = linkTo(CustomerController.class).slash(customerId);

        final Resource<CustomerDTO> customerDto = new Resource<>(customerAdapter.customer(customerId));
        customerDto.add(selfLink.withSelfRel());
        customerDto.add(homeLink());
        customerDto.add(customersLink());
        customerDto.add(updateLink.withRel(REL_UPDATE));
        customerDto.add(deleteLink.withRel(REL_DELETE));

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping(path = "/" + CREATE,  consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> create(@RequestBody @Valid final CustomerDTO customerDto) {

        customerAdapter.create(customerDto);

        return ResponseEntity.created(URI.create(customersLink().getHref())).build();
    }

    @PutMapping(path = "/{customerId}", consumes = APPLICATION_JSON_V1_VALUE)
    public ResponseEntity<?> update(@PathVariable final long customerId,
                                    @RequestBody @Valid final CustomerDTO customerDto) {

        isTrue(customerId == customerDto.id, "Customer Id does not match");

        customerAdapter.update(customerDto);

        return ResponseEntity.ok().location(URI.create(customersLink().getHref())).build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<?> delete(@PathVariable final long customerId) {

        customerAdapter.delete(customerId);

        return ResponseEntity.ok().location(URI.create(customersLink().getHref())).build();
    }

    private List<Link> customerLinks() {
        final CustomersDTO customers = customerAdapter.customers();

        return customers.customers.stream()
                                  .map(cust -> linkTo(methodOn(CustomerController.class).customer(cust.id)).withRel(REL_CUSTOMER_PREFIX + cust.id))
                                  .collect(Collectors.toList());
    }
}
