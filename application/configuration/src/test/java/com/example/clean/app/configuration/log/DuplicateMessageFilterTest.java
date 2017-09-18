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

        // messages are denied since the repetitions have been exceeded
        // due to customerId being ignored
        // resulting in the messages being considered the same
        assertThat(logMessage(dmf, MESSAGE, customerId("7"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("8"))).isEqualTo(FilterReply.DENY);
    }

    @Test
    public void shouldAllowRepetitionsWhenCacheSizeExceeded() {
        final DuplicateMessageFilter dmf = new DuplicateMessageFilter();
        dmf.setAllowedRepetitions(5);
        dmf.setCacheSize(2);
        dmf.start();

        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("2"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("3"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("4"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("5"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("6"))).isEqualTo(FilterReply.NEUTRAL);

        // exceed the cache
        assertThat(logMessage(dmf, "Another message", null)).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, "Yet another message", null)).isEqualTo(FilterReply.NEUTRAL);

        // messages are accepted since the cache has been exceeded
        assertThat(logMessage(dmf, MESSAGE, customerId("7"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("8"))).isEqualTo(FilterReply.NEUTRAL);
    }

    private String[] customerId(final String id) {
        return new String[]{id};
    }

    private FilterReply logMessage(final TurboFilter dmf, final String message, final String[] params) {
        return dmf.decide(null, null, null, message, params, null);
    }
}