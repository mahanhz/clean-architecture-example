package com.example.clean.app.core.domain;

import com.example.clean.app.core.helper.NoException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.clean.app.core.domain.LastName.MAX_LENGTH;
import static com.example.clean.app.core.helper.JUnitParamHelper.invalidMatching;
import static com.example.clean.app.core.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class LastNameTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  Smith  ";

    @Test
    @Parameters(method = "values")
    public void testCreation(final Class<? extends Exception> expectedException,
                              final String value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableLastName.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void shouldTrimValue() {
        final ImmutableLastName lastName = ImmutableLastName.of(VALUE_WITH_WHITE_SPACE);

        assertThat(lastName.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "Doe"},
                {valid(), StringUtils.repeat("a", MAX_LENGTH)},
                {invalidMatching(IllegalArgumentException.class), ""},
                {invalidMatching(IllegalArgumentException.class), " "},
                {invalidMatching(IllegalArgumentException.class), StringUtils.repeat("b", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}