package com.example.clean.app.configuration.log;

import ch.qos.logback.classic.turbo.DuplicateMessageFilter;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DuplicateMessageFilterTest {

    private static final String MESSAGE = "Getting customer {}";

    @Test
    public void shouldOnlyConsiderRawMessage() {
        final DuplicateMessageFilter dmf = new DuplicateMessageFilter();
        dmf.setAllowedRepetitions(5);
        dmf.start();

        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("2"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("3"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("4"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("5"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("6"))).isEqualTo(FilterReply.NEUTRAL);

        assertThat(logMessage(dmf, MESSAGE, customerId("7"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("8"))).isEqualTo(FilterReply.DENY);
    }

    private String[] customerId(final String id) {
        return new String[]{id};
    }

    private FilterReply logMessage(final TurboFilter dmf, final String message, final String[] params) {
        return dmf.decide(null, null, null, message, params, null);
    }
}