package com.combitech.commutelogger.backend.domain.exceptions;

import com.combitech.commutelogger.backend.domain.exceptions.handling.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Getter
@Accessors(fluent = true)
public abstract class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public void write(ObjectMapper mapper, HttpServletResponse response) throws IOException {
        response.setStatus(httpStatus()
                .value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (PrintWriter writer = response.getWriter()) {
            String body = mapper.writeValueAsString(new ApiError(getMessage()));
            writer.print(body);
        }
    }
}
