package com.metrodataacademy.exception;

import com.metrodataacademy.domain.dto.response.ResBaseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResBaseDto> handleException(Exception exception) {
        ResBaseDto message = new ResBaseDto(null, exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResBaseDto> handleException(MethodArgumentNotValidException exception) {
        HashMap<String, List<String>> errorJson = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();

            if (errorJson.containsKey(fieldName)) {
                errorJson.get(fieldName).add(errorMessage);
            } else {
                List<String> errors = new ArrayList<>();
                errors.add(errorMessage);
                errorJson.put(fieldName, errors);
            }
        });

        ResBaseDto message = new ResBaseDto(errorJson, "Validation error");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String message = "Method Not Allowed: " + ex.getMethod();
        ResBaseDto errorDetails = new ResBaseDto(null, message);
        return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

}