package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.NameDTO;
import com.example.clean.app.core.domain.*;
import org.apache.commons.lang3.StringUtils;

public final class CustomerFactory {

    private CustomerFactory() {
        // To prevent instantiation
    }

    public static Customer customer(final CustomerDTO customer) {
        return Customer.of(Customer.Id.of(customer.id), name(customer.name));
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
