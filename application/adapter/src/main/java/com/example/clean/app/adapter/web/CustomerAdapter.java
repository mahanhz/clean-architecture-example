package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomersDTO;
import com.example.clean.app.adapter.web.api.NameDTO;
import com.example.clean.app.core.boundary.enter.CustomerEditService;
import com.example.clean.app.core.boundary.enter.CustomerService;
import com.example.clean.app.core.domain.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

public class CustomerAdapter {

    private CustomerService customerService;
    private CustomerEditService customerEditService;

    public CustomerAdapter(final CustomerService customerService, final CustomerEditService customerEditService) {
        this.customerService = notNull(customerService);
        this.customerEditService = notNull(customerEditService);
    }

    public CustomersDTO customers() {
        final List<Customer> customers = customerService.customers();

        return new CustomersDTO(customers(customers));
    }

    public CustomerDTO customer(final long customerId) {
        final Customer customer = customerService.customer(Customer.Id.of(customerId));

        return customer(customer);
    }

    public void create(final CustomerDTO customerDTO) {
        notNull(customerDTO);

        customerEditService.create(customer(customerDTO));
    }

    public void update(final CustomerDTO customerDTO) {
        notNull(customerDTO);

        customerEditService.update(customer(customerDTO));
    }

    public void delete(final long customerId) {
        customerEditService.delete(Customer.Id.of(customerId));
    }

    private List<CustomerDTO> customers(final List<Customer> customers) {
        return customers.stream()
                        .map(this::customer)
                        .collect(toList());
    }

    private CustomerDTO customer(final Customer customer) {
        return new CustomerDTO(customer.getId().getId(),
                               name(customer.getName()));
    }

    private Customer customer(final CustomerDTO customer) {
        return Customer.of(Customer.Id.of(customer.id), name(customer.name));
    }

    private NameDTO name(final Name name) {
        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return new NameDTO(firstName == null ? "" : firstName.value(),
                           middleName == null ? "" : middleName.value(),
                           lastName == null ? "" : lastName.value(),
                           suffix == null ? "" : suffix.value());
    }

    private static Name name(final NameDTO name) {
        final String firstName = name.firstName;
        final String middleName = StringUtils.defaultIfBlank(name.middleName, "");
        final String lastName = name.lastName;
        final String suffix = StringUtils.defaultIfBlank(name.suffix, "");

        return ImmutableName.builder()
                            .firstName(ImmutableFirstName.of(firstName))
                            .middleName(ImmutableMiddleName.of(middleName))
                            .lastName(ImmutableLastName.of(lastName))
                            .suffix(ImmutableSuffix.of(suffix))
                            .build();
    }
}
