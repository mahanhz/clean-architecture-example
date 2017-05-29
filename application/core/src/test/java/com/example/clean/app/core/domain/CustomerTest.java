package com.example.clean.app.core.domain;

import com.example.clean.app.core.helper.NoException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.clean.app.core.helper.DomainModelHelper.name;
import static com.example.clean.app.core.helper.JUnitParamHelper.invalidMatching;
import static com.example.clean.app.core.helper.JUnitParamHelper.valid;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CustomerTest {

    @Test
    @Parameters(method = "values")
    public void test_creation(final Class<? extends Exception> expectedException,
                              final long id,
                              final Name name) {
        Class<? extends Exception> actualException = NoException.class;

        try {
            Customer.of(Customer.Id.of(id), name);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }
        assertThat(actualException).isEqualTo(expectedException);
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                { valid(), 123L, name() },
                { invalidMatching(NullPointerException.class), 123L, null }
        };
    }
}