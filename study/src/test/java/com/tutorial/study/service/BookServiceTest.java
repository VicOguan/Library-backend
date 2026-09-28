package com.tutorial.study.service;

import com.tutorial.study.dto.BookRequest;
import com.tutorial.study.dto.BookResponse;
import com.tutorial.study.entity.Book;
import com.tutorial.study.mapper.BookMapper;
import com.tutorial.study.repository.BookRepository;
import com.tutorial.study.repository.BorrowRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookService bookService;


    @Test
    void shouldAddBookSuccessfully(){
        BookRequest request = new BookRequest();

        request.setTitle("java");
        request.setAuthor("java");

        Book book = new Book();

        book.setTitle("java");
        book.setAuthor("java");

        Book savedBook = new Book();

        savedBook.setId(16);
        savedBook.setTitle("java");
        savedBook.setAuthor("java");

        BookResponse response = new BookResponse(1, "java", "java");

        when(bookMapper.toEntity(request)).thenReturn(book);

        when(bookRepository.save(book)).thenReturn(savedBook);

        when(bookMapper.toResponse(savedBook)).thenReturn(response);

        BookResponse result = bookService.addBook(request);

        assertEquals(1, result.getId());
        assertEquals("java", result.getTitle());
        assertEquals("java", result.getAuthor());

        verify(bookMapper).toEntity(request);
        verify(bookRepository).save(book);
        verify(bookMapper).toResponse(savedBook);

    }


}
