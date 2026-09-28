package com.tutorial.study.exception;

public class MaxBorrowLimitExceededException extends RuntimeException{
    public MaxBorrowLimitExceededException(String message){
        super(message);
    }
}
