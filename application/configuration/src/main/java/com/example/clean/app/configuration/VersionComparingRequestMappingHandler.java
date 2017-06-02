package com.example.clean.app.configuration;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;

public class VersionComparingRequestMappingHandler extends RequestMappingHandlerMapping {

    // Use the latest versioned request mapping if more than one matches
    // e.g. /api/customers with application/vnd.example.clean.v2+json
    // should be selected over application/vnd.example.clean.v1+json
    // so that the api returns the latest response
    @Override
    protected Comparator<RequestMappingInfo> getMappingComparator(final HttpServletRequest request) {
        return (info1, info2) -> info2.compareTo(info1, request);
    }
}
