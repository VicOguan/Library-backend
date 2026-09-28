package com.tutorial.study.exception;

public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException(String message){
        super(message);
    }
}
