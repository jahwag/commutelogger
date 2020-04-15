package com.combitech.commutelogger.backend.domain.exceptions.handling;

import com.combitech.commutelogger.backend.domain.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleException(Exception exception) {
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;

            return ResponseEntity.status(apiException.httpStatus())
                                 .body(new ApiError(apiException.getMessage()));
        } else if (exception instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .build();
        } else if (exception instanceof DataIntegrityViolationException) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                 .body(new ApiError("Cascade deletion not implemented."));
        } else {
            log.error("", exception);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ApiError("Oh no! Something bad happened. Please come back later when we fixed that problem. Thanks."));
    }

}
