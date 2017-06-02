package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomerV2DTO;
import com.example.clean.app.adapter.web.api.NameDTO;
import com.example.clean.app.adapter.web.api.NameV2DTO;
import com.example.clean.app.core.domain.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class CustomerDTOFactory {

    private CustomerDTOFactory() {
        // To prevent instantiation
    }

    public static List<CustomerDTO> customers(final List<Customer> customers) {
        return customers.stream()
                        .map(CustomerDTOFactory::customer)
                        .collect(toList());
    }

    public static List<CustomerV2DTO> customersV2(final List<Customer> customers) {
        return customers.stream()
                        .map(CustomerDTOFactory::customerV2)
                        .collect(toList());
    }

    public static CustomerDTO customer(final Customer customer) {
        return new CustomerDTO(customer.getId().getId(),
                               name(customer.getName()));
    }

    public static CustomerV2DTO customerV2(final Customer customer) {
        return new CustomerV2DTO(customer.getId().getId(),
                                 nameV2(customer.getName()));
    }

    private static NameDTO name(final Name name) {
        final FirstName firstName = name.firstName();
        final MiddleName middleName = name.middleName();
        final LastName lastName = name.lastName();
        final Suffix suffix = name.suffix();

        return new NameDTO(firstName == null ? "" : firstName.value(),
                           middleName == null ? "" : middleName.value(),
                           lastName == null ? "" : lastName.value(),
                           suffix == null ? "" : suffix.value());
    }

    private static NameV2DTO nameV2(final Name name) {
        final FirstName firstName = name.firstName();
        final LastName lastName = name.lastName();

        return new NameV2DTO(firstName.value() + " " + lastName.value());
    }
}
