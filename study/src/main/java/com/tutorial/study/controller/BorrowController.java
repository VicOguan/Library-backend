package com.tutorial.study.controller;

import com.tutorial.study.dto.BorrowRecordResponse;
import com.tutorial.study.entity.BorrowRecord;
import com.tutorial.study.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    private final BookService bookService;

    public BorrowController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/books/{id}")
    public ResponseEntity<Void> borrowBook(@PathVariable("id") int bookId){
            bookService.borrowBook(bookId);
            return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{recordId}")
    public ResponseEntity<Void> returnBook(@PathVariable("recordId") int recordId){
        bookService.returnBook(recordId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-books")
    public ResponseEntity<List<BorrowRecordResponse>> getMyBorrowedBooks(){
        return ResponseEntity.ok(bookService.getMyBorrowedBooks());
    }

}
