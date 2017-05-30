package com.example.clean.app.web.controller.exception;

import com.example.clean.app.web.controller.MediaTypes;
import com.example.clean.app.web.controller.ControllerPackageMarker;
import com.fasterxml.uuid.Generators;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.example.clean.app.web.controller.exception.VndErrorFactory.vndErrors;

@RestControllerAdvice(basePackageClasses = { ControllerPackageMarker.class })
public class GlobalExceptionRestHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionRestHandlerController.class);

    public static final String ERROR_ID = "errorId";
    public static final String STATUS = "status";
    public static final String PATH = "path";
    public static final String MESSAGE = "message";

    @ExceptionHandler
    public ResponseEntity<VndErrors> handleException(final HttpServletRequest request,
                                                     final HttpServletResponse response,
                                                     final Throwable throwable) {

        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        // TODO: MESSAGE should maybe return a standard value in Production as it may expose too much information.
        final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
        final ImmutableMap<String, String> errorAttributes = ImmutableMap.of(ERROR_ID, errorId.toString(),
                                                                             STATUS, httpStatus.toString(),
                                                                             PATH, request.getRequestURI(),
                                                                             MESSAGE, defaulted(throwable.getMessage()));

        return new ResponseEntity<>(vndErrors(errorAttributes), headers(), httpStatus);
    }

    private String defaulted(final String value) {
        return StringUtils.defaultIfBlank(value, "");
    }

    private HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypes.APPLICATION_VND_ERROR_JSON);
        return headers;
    }
}
