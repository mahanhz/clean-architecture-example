package com.example.clean.app.data.helper;

import com.example.clean.app.core.domain.Customer;
import com.example.clean.app.data.jpa.entity.CustomerEntity;
import com.example.clean.app.data.jpa.entity.Name;

public final class JpaRepositoryHelper {

    private JpaRepositoryHelper() {
        // To prevent instantiation
    }

    public static CustomerEntity customerEntity() {
        return new CustomerEntity(123L, name());
    }

    public static Name name() {
        return Name.create("John", "", "Doe", "");
    }

    public static CustomerEntity customerEntity(final Customer customer) {
        final long id = customer.getId().getId();
        final Name name = name(customer.getName());

        return new CustomerEntity(id, name);
    }

    public static Name name(final com.example.clean.app.core.domain.Name customerName) {
        final String firstName = customerName.firstName().value();
        final String middleName = customerName.middleName().value();
        final String lastName = customerName.lastName().value();
        final String suffix = customerName.suffix().value();

        return Name.create(firstName, middleName, lastName, suffix);
    }
}
