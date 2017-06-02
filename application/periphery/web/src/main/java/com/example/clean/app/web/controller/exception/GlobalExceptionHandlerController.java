package com.example.clean.app.web.controller.exception;

import com.example.clean.app.web.controller.ControllerPackageMarker;
import com.example.clean.app.web.controller.MediaTypes;
import com.fasterxml.uuid.Generators;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.clean.app.web.controller.exception.VndErrorFactory.vndErrors;

@RestControllerAdvice(basePackageClasses = { ControllerPackageMarker.class })
public class GlobalExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    public static final String ERROR_ID = "errorId";
    public static final String ERROR = "error";
    public static final String STATUS = "status";
    public static final String PATH = "path";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";

    @ExceptionHandler
    public ResponseEntity<VndErrors> handleException(final HttpServletRequest request,
                                                     final HttpServletResponse response,
                                                     final Throwable throwable) {

        final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());

        return new ResponseEntity<>(vndErrors(errorAttributes(request, response, httpStatus, throwable)),
                                    headers(),
                                    httpStatus);
    }

    private HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypes.APPLICATION_VND_ERROR_JSON);
        return headers;
    }

    private Map<String, String> errorAttributes(final HttpServletRequest request,
                                                final HttpServletResponse response,
                                                final HttpStatus httpStatus,
                                                final Throwable throwable) {
        final UUID errorId = Generators.timeBasedGenerator().generate();
        LOGGER.error("ErrorId: {} references the following error: ", errorId, throwable);

        final Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_ID, errorId.toString());
        errorMap.put(ERROR, "Oops! Something went wrong!");
        errorMap.put(STATUS, httpStatus.toString());
        errorMap.put(PATH, request.getRequestURI());
        errorMap.put(MESSAGE, "Record the errorId when reporting this issue");
        errorMap.put(TIMESTAMP, new Date().toString());

        return errorMap;
    }
}
