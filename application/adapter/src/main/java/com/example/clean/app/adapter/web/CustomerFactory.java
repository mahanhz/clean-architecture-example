package com.example.clean.app.adapter.web;

import com.example.clean.app.adapter.web.api.CustomerDTO;
import com.example.clean.app.adapter.web.api.CustomerV2DTO;
import com.example.clean.app.adapter.web.api.NameDTO;
import com.example.clean.app.adapter.web.api.NameV2DTO;
import com.example.clean.app.core.domain.*;
import org.apache.commons.lang3.StringUtils;

public final class CustomerFactory {

    private CustomerFactory() {
        // To prevent instantiation
    }

    public static Customer customer(final CustomerDTO customer) {
        return Customer.of(Customer.Id.of(customer.id), name(customer.name));
    }

    public static Customer customer(final CustomerV2DTO customer) {
        return Customer.of(Customer.Id.of(customer.id), nameV2(customer.name));
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

    private static Name nameV2(final NameV2DTO name) {
        final String[] nameParts = StringUtils.split(" ");
        final String firstName = nameParts[0];
        final String lastName = nameParts[1];

        return ImmutableName.builder()
                            .firstName(ImmutableFirstName.of(firstName))
                            .middleName(ImmutableMiddleName.of(""))
                            .lastName(ImmutableLastName.of(lastName))
                            .suffix(ImmutableSuffix.of(""))
                            .build();
    }
}
