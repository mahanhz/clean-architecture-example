package com.example.clean.app.data.jpa.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class NameTest {

    @Test
    public void equalsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(Name.class).verify();
    }
}