package com.example.clean.app.configuration.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ExpiringDuplicateMessageFilterTest {

    private static final String MESSAGE         = "Getting customer {}";
    private static final String SECURITY_MARKER = "SECURITY";
    private static final String NORMAL_MARKER   = "NORMAL";

    @Test
    public void shouldConsiderMessageSimilarity() {
        final ExpiringDuplicateMessageFilter dmf = new ExpiringDuplicateMessageFilter();
        dmf.setAllowedRepetitions(4);
        dmf.start();

        // customerId parameter is taken into consideration
        // so all are different and allowed
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("2"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("3"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("4"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("5"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("6"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("7"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("8"))).isEqualTo(FilterReply.NEUTRAL);
    }

    @Test
    public void shouldAllowOnlyFourRepetitions() {
        final ExpiringDuplicateMessageFilter dmf = new ExpiringDuplicateMessageFilter();
        dmf.setAllowedRepetitions(4);
        dmf.start();

        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("2"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("3"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);

        // messages are denied as the repetition count for customer 1 has been exceeded
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
    }

    @Test
    public void shouldReappearAfterCacheHasExpired() throws Exception {
        final ExpiringDuplicateMessageFilter dmf = new ExpiringDuplicateMessageFilter();
        dmf.setAllowedRepetitions(4);
        dmf.setExpireAfterWriteSeconds(2);
        dmf.start();

        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);

        // messages are denied as the repetition count for customer 1 has been exceeded
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);

        Thread.sleep(3000);

        // messages are allowed as the cache for customer 1 has expired
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
    }

    @Test
    public void shouldAllowRepetitionsWhenCacheSizeExceeded() {
        final ExpiringDuplicateMessageFilter dmf = new ExpiringDuplicateMessageFilter();
        dmf.setAllowedRepetitions(4);
        dmf.setCacheSize(2);
        dmf.start();

        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);

        // exceed the cache
        Assertions.assertThat(logMessage(dmf, "Another message", null)).isEqualTo(FilterReply.NEUTRAL);
        Assertions.assertThat(logMessage(dmf, "Yet another message", null)).isEqualTo(FilterReply.NEUTRAL);

        // messages are accepted since the cache has been exceeded
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
    }

    @Test
    public void shouldExcludeMarkers() throws Exception {
        final ExpiringDuplicateMessageFilter dmf = new ExpiringDuplicateMessageFilter();
        dmf.setAllowedRepetitions(4);
        dmf.setExcludeMarkers(SECURITY_MARKER);
        dmf.start();

        // There is no limitation on security markers as they are excluded
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(SECURITY_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);

        // The limit of 4 repetitions still applies to non security markers
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.NEUTRAL);
        assertThat(logMessage(marker(NORMAL_MARKER), dmf, MESSAGE, customerId("1"))).isEqualTo(FilterReply.DENY);
    }

    private String[] customerId(final String id) {
        return new String[]{id};
    }

    private FilterReply logMessage(final Marker marker,
                                   final TurboFilter dmf,
                                   final String message,
                                   final String[] params) {
        return dmf.decide(marker, logger(), null, message, params, null);
    }

    private FilterReply logMessage(final TurboFilter dmf,
                                   final String message,
                                   final String[] params) {
        return logMessage(null, dmf, message, params);
    }

    private Marker marker(final String marker) {
        return MarkerFactory.getMarker(marker);
    }

    private Logger logger() {
        return (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.example");
    }
}