package com.tutorial.study.controller;

import com.tutorial.study.dto.ApiResponse;
import com.tutorial.study.dto.BookRequest;
import com.tutorial.study.dto.BookResponse;
import com.tutorial.study.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getBook(){
        return ResponseEntity.ok(bookService.getBook());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable int id) {
        BookResponse response = bookService.getBookId(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/books")
    public ResponseEntity<ApiResponse<BookResponse>> addBook(
            @Valid @RequestBody BookRequest request){

        BookResponse bookResponse = bookService.addBook(request);

        ApiResponse<BookResponse> response = new ApiResponse<>(
                "Added Successfully!",
                bookResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable int id,
                                                   @Valid @RequestBody BookRequest request){
        BookResponse bookResponse =  bookService.updateBook(id, request);

        if (bookResponse == null){
            return ResponseEntity.notFound().build();
        }

        ApiResponse<BookResponse> response = new ApiResponse<>(
                "Updated Successfully!",
                bookResponse
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable int id){

         bookService.deleteBook(id);

         ApiResponse<Void> response = new ApiResponse<>(
                 "Deleted Successfully!",
                 null
         );

        return ResponseEntity.ok(response);
    }

}
