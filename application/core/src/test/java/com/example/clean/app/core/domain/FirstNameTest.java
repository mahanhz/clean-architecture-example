package com.example.clean.app.core.domain;

import com.example.clean.app.core.helper.NoException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.clean.app.core.domain.FirstName.MAX_LENGTH;
import static com.example.clean.app.core.helper.JUnitParamHelper.invalidMatching;
import static com.example.clean.app.core.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FirstNameTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  Arnold  ";

    @Test
    @Parameters(method = "values")
    public void testCreation(final Class<? extends Exception> expectedException,
                              final String value) {
        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableFirstName.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void shouldTrimValue() {
        final ImmutableFirstName firstName = ImmutableFirstName.of(VALUE_WITH_WHITE_SPACE);

        assertThat(firstName.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "John"},
                {valid(), repeat("x", MAX_LENGTH)},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(IllegalArgumentException.class), " "},
                {invalidMatching(IllegalArgumentException.class), repeat("x", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}