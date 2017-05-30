package com.example.clean.app.web.controller.exception;

import org.springframework.hateoas.VndErrors;

import java.util.List;
import java.util.Map;

import static com.example.clean.app.web.controller.CommonLinks.homeLink;
import static java.util.stream.Collectors.toList;

public final class VndErrorFactory {

    private VndErrorFactory() {
        // prevent instantiation
    }

    public  static VndErrors vndErrors(final Map<String, String> errorAttributes) {
        final List<VndErrors.VndError> vndErrors = errorAttributes.entrySet().stream()
                                                                  .map(VndErrorFactory::vndError)
                                                                  .collect(toList());

        return new VndErrors(vndErrors);
    }

    private static VndErrors.VndError vndError(final Map.Entry<String, String> entry) {
        return new VndErrors.VndError(entry.getKey(), entry.getValue(), homeLink());
    }
}
