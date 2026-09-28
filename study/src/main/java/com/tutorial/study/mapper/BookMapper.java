package com.tutorial.study.mapper;

import com.tutorial.study.entity.Book;
import com.tutorial.study.dto.BookRequest;
import com.tutorial.study.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book toEntity(BookRequest request){

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setAvailable(true);
        return book;
    }

    public BookResponse toResponse(Book book){
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.isAvailable()
        );
    }
}
