package com.example.clean.app.core.domain;

import com.example.clean.app.core.helper.NoException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.clean.app.core.domain.Suffix.MAX_LENGTH;
import static com.example.clean.app.core.helper.JUnitParamHelper.invalidMatching;
import static com.example.clean.app.core.helper.JUnitParamHelper.valid;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SuffixTest {

    private static final String VALUE_WITH_WHITE_SPACE = "  JR  ";

    @Test
    @Parameters(method = "values")
    public void testCreation(final Class<? extends Exception> expectedException,
                              final String value) {

        Class<? extends Exception> actualException = NoException.class;

        try {
            ImmutableSuffix.of(value);
        } catch (Exception ex) {
            actualException = ex.getClass();
        }

        assertThat(actualException).isEqualTo(expectedException);
    }

    @Test
    public void shouldTrimValue() {
        final ImmutableSuffix suffix = ImmutableSuffix.of(VALUE_WITH_WHITE_SPACE);

        assertThat(suffix.value()).isEqualTo(trim(VALUE_WITH_WHITE_SPACE));
    }

    @SuppressWarnings("unused")
    private Object values() {
        return new Object[][]{
                {valid(), "IV"},
                {valid(), repeat("I", MAX_LENGTH)},
                {valid(), ""},
                {invalidMatching(IllegalArgumentException.class), repeat("I", MAX_LENGTH + 1)},
                {invalidMatching(NullPointerException.class), null}
        };
    }
}