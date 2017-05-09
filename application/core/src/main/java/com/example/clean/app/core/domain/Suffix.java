package com.example.clean.app.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.immutables.value.Value;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;

@Value.Immutable
public interface Suffix {
    int MAX_LENGTH = 10;

    @Value.Parameter
    String value();

    @Value.Check
    default Suffix check() {
        final String trimmed = trim(value());
        isTrue(trimmed.length() <= MAX_LENGTH);

        if (!StringUtils.equals(value(), trimmed)) {
            return ImmutableSuffix.of(trimmed);
        }

        return this;
    }
}
