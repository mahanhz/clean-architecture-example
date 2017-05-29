package com.example.clean.app.core.domain;

import com.example.clean.app.core.helper.NoException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.clean.app.core.helper.DomainModelHelper.*;
import static com.example.clean.app.core.helper.JUnitParamHelper.invalidMatching;
import static com.example.clean.app.core.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class NameTest {

    @Test
    @Parameters(method = "values")
    public void testCreation(final Class<? extends Exception> expectedException,
                              final FirstName firstName,
                              final MiddleName middleName,
                              final LastName lastName,
                              final Suffix suffix) {
        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableName.builder()
                         .firstName(firstName)
                         .middleName(middleName)
                         .lastName(lastName)
                         .suffix(suffix)
                         .build();
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), firstName(), middleName(), lastName(), suffix() },
                { valid(), firstName(), null, lastName(), null },
                { invalidMatching(NullPointerException.class), null, middleName(), lastName(), suffix() },
                { invalidMatching(NullPointerException.class), firstName(), middleName(), null, suffix() },
                { invalidMatching(NullPointerException.class), null, middleName(), null, suffix() }
        };
    }
}