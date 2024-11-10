package net.javaguides.__backend.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Duplicate Email Exception
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email already exists");
    }

//    @ExceptionHandler(DuplicateEmailException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)  // Set HTTP 409 Conflict for duplicate email
//    public String handleDuplicateEmailException(DuplicateEmailException ex) {
//        return ex.getMessage();  // Customize the response as needed
//    }

    // You can add other custom exception handlers here if needed
}
