package com.crypto.trading.system.cryptotradingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> invalidDataException() {
        return ResponseEntity.badRequest().body(new ErrorResponse("FA", "Mandatory item not found"));
    }

    @ExceptionHandler(PriceMismatchException.class)
    public ResponseEntity<ErrorResponse> priceMismatchException(PriceMismatchException priceMismatchException) {
        return ResponseEntity.badRequest().body(new ErrorResponse("FA", "Not enough " + priceMismatchException.getMessage()));
    }

}
