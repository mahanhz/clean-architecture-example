package com.example.clean.app.data.helper;

import com.example.clean.app.core.domain.*;

public final class DomainModelHelper {

    public static Customer customer() {
        return Customer.of(Customer.Id.of(123L), name());
    }

    public static Name name() {
        return ImmutableName.builder()
                            .firstName(firstName())
                            .middleName(middleName())
                            .lastName(lastName())
                            .suffix(suffix())
                            .build();
    }

    public static FirstName firstName() {
        return ImmutableFirstName.of("firstName");
    }

    public static MiddleName middleName() {
        return ImmutableMiddleName.of("middleName");
    }

    public static LastName lastName() {
        return ImmutableLastName.of("lastName");
    }

    public static Suffix suffix() {
        return ImmutableSuffix.of("suffix");
    }
}
