package com.tutorial.study.service;

import com.tutorial.study.dto.BookRequest;
import com.tutorial.study.dto.BookResponse;
import com.tutorial.study.dto.BorrowRecordResponse;
import com.tutorial.study.entity.AppUser;
import com.tutorial.study.entity.Book;
import com.tutorial.study.entity.BorrowRecord;
import com.tutorial.study.exception.BookAlreadyBorrowedException;
import com.tutorial.study.exception.BookNotFoundException;
import com.tutorial.study.exception.MaxBorrowLimitExceededException;
import com.tutorial.study.exception.UserNotFoundException;
import com.tutorial.study.mapper.BookMapper;
import com.tutorial.study.repository.BookRepository;
import com.tutorial.study.repository.BorrowRecordRepository;
import com.tutorial.study.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final int MAX_BORROW_LIMIT = 3;
    private static final int BORROW_DAYS = 14;

    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper,
                       BorrowRecordRepository borrowRecordRepository, UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.borrowRecordRepository = borrowRecordRepository;
        this.userRepository = userRepository;
    }

    private AppUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUserName(username)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    public List<BookResponse> getBook(){
        return bookRepository
                .findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    public BookResponse getBookId(int id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID:"+id+" not found!"));
        return bookMapper.toResponse(book);
    }

    public void deleteBook(int id){
        if (!bookRepository.existsById(id)){
            throw new BookNotFoundException("Book with ID:"+id+" not found!");
        }
        bookRepository.deleteById(id);
    }

    public BookResponse addBook(BookRequest request){
        Book book = bookMapper.toEntity(request);
        Book saveBook = bookRepository.save(book);
        return bookMapper.toResponse(saveBook);
    }

    public BookResponse updateBook(int id, BookRequest request){
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID:"+id+" not found!"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        Book updateBook = bookRepository.save(book);
        return bookMapper.toResponse(updateBook);
    }

    @Transactional
    public void borrowBook(int bookId){
        AppUser user = getUser();
        long activeBorrow = borrowRecordRepository.countByMemberIdAndStatus(user.getId(), "ACTIVE");
        if (activeBorrow >= MAX_BORROW_LIMIT){
            throw new MaxBorrowLimitExceededException("You have reached the maximum borrow limit.");
        }
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID:"+bookId+" not found!"));
        if (!book.isAvailable()){
            throw new BookAlreadyBorrowedException("Book with ID:"+bookId+" is already borrowed!");
        }
        book.setAvailable(false);
        bookRepository.save(book);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusDays(BORROW_DAYS);

        BorrowRecord record = new BorrowRecord();
        record.setMemberId(user.getId());
        record.setBookId(bookId);
        record.setTitle(book.getTitle());
        record.setAuthor(book.getAuthor());
        record.setBorrowedAt(now);
        record.setDueDate(dueDate);
        record.setStatus("ACTIVE");

        borrowRecordRepository.save(record);
    }

    @Transactional
    public void returnBook(int recordId){
        AppUser user = getUser();

        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new BookNotFoundException("Borrow record with ID:" + recordId + " not found!"));

        if (record.getMemberId() != user.getId()) {
            throw new IllegalStateException("Unauthorized operation for this record.");
        }

        Book book = bookRepository.findById(record.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book with ID:" + record.getBookId() + " not found!"));

        book.setAvailable(true);
        bookRepository.save(book);

        record.setStatus("RETURNED");
        record.setReturnedAt(LocalDateTime.now());
        borrowRecordRepository.save(record);
    }

    public List<BorrowRecordResponse> getMyBorrowedBooks() {
        AppUser user = getUser();
        LocalDateTime now = LocalDateTime.now();

        return borrowRecordRepository.findByMemberId(user.getId())
                .stream()
                .map(record -> {
                    String currentStatus = record.getStatus() != null ? record.getStatus() :
                            (record.getReturnedAt() == null ? "ACTIVE" : "RETURNED");

                    boolean isOverdue = "ACTIVE".equalsIgnoreCase(record.getStatus()) &&
                            record.getDueDate() != null &&
                            now.isAfter(record.getDueDate());

                    return new BorrowRecordResponse(
                            record.getId(),
                            record.getMemberId(),
                            record.getBookId(),
                            record.getTitle(),
                            record.getAuthor(),
                            record.getBorrowedAt(),
                            record.getDueDate(),
                            record.getReturnedAt(),
                            currentStatus,
                            isOverdue
                    );
                }).collect(Collectors.toList());
    }
}