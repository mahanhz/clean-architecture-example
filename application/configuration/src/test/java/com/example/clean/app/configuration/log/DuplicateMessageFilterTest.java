package com.example.clean.app.configuration.log;

import ch.qos.logback.classic.turbo.DuplicateMessageFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DuplicateMessageFilterTest {

    private static final String WELCOME_MSG = "Welcome ";
    private static final String TAKE_A_SEAT_MSG = "Take a seat ";
    private static final String[] USER_A = { "User A" };
    private static final String[] USER_B = { "User B" };

    @Test
    public void shouldOnlyConsiderRawMessage() {
        final DuplicateMessageFilter dmf = new DuplicateMessageFilter();
        dmf.setAllowedRepetitions(0);
        dmf.start();

        assertThat(logMessage(dmf, WELCOME_MSG, USER_A)).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, TAKE_A_SEAT_MSG, USER_A)).isEqualTo(FilterReply.NEUTRAL);

        // repetition
        assertThat(logMessage(dmf, WELCOME_MSG, USER_B)).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, TAKE_A_SEAT_MSG, USER_B)).isEqualTo(FilterReply.DENY);
    }

    private FilterReply logMessage(final DuplicateMessageFilter dmf, final String message, final String[] params) {
        return dmf.decide(null, null, null, message, params, null);
    }
}