package com.example.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<String>
    handleIllegalArgument(
            IllegalArgumentException exception) {


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }


    @ExceptionHandler(
            RuntimeException.class
    )
    public ResponseEntity<String>
    handleRuntimeException(
            RuntimeException exception) {

        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}