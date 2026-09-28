package com.tutorial.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse>
        handleValidationException(MethodArgumentNotValidException exception){
            Map<String, String> errors = new HashMap<>();
            exception.getBindingResult()
                    .getFieldErrors()
                    .forEach(error -> errors
                            .put(
                                    error.getField(),
                                    error.getDefaultMessage()
                            )
                    );
            ErrorResponse errorResponse = new ErrorResponse(
                    400,
                    "Validation failed",
                    errors
            );

            return ResponseEntity.badRequest()
                    .body(errorResponse);
        }

        @ExceptionHandler(BookNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException exception){
            ErrorResponse response = new ErrorResponse(
                    404,
                    exception.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        @ExceptionHandler(BookAlreadyBorrowedException.class)
        public ResponseEntity<ErrorResponse> handleBookAlreadyBorrowed(
                BookAlreadyBorrowedException e){
            ErrorResponse response = new ErrorResponse(
                    409,
                    e.getMessage(),
                    null
            );

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(response);
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception){
            ErrorResponse response = new ErrorResponse(
                    404,
                    exception.getMessage(),
                    null
            );
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        @ExceptionHandler(MaxBorrowLimitExceededException.class)
        public ResponseEntity<ErrorResponse> handleMaxBorrowLimitExceeded(MaxBorrowLimitExceededException exception){
            ErrorResponse response = new ErrorResponse(
                    400,
                    exception.getMessage(),
                    null
            );
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
}
