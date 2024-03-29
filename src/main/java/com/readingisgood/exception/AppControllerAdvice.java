package com.readingisgood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource is not available, please change your request.");
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleCustomException(ServiceException serviceException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceException.getMessage());
    }
}
