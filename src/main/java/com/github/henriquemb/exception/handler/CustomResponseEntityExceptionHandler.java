package com.github.henriquemb.exception.handler;

import com.github.henriquemb.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handlerAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ExceptionResponse> handleFileStorageException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleFileNotFoundException(Exception exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
