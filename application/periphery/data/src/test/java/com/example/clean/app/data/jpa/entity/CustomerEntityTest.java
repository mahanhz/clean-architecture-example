package com.example.clean.app.data.jpa.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class CustomerEntityTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(CustomerEntity.class).suppress(Warning.IDENTICAL_COPY_FOR_VERSIONED_ENTITY).verify();
    }
}