package com.tutorial.study.dto;

import java.time.LocalDateTime;

public class BorrowRecordResponse {
    private int id;
    private int memberId;
    private int bookId;
    private String title;
    private String author;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;
    private String status;
    private boolean overdue;

    public BorrowRecordResponse(int id, int memberId, int bookId, String title, String author,
                                LocalDateTime borrowedAt, LocalDateTime dueDate, LocalDateTime returnedAt,
                                String status, boolean overdue) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
        this.status = status;
        this.overdue = overdue;
    }

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public void setBorrowedAt(LocalDateTime borrowedAt) {this.borrowedAt = borrowedAt; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getReturnedAt() { return returnedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean getOverdue() { return overdue; }
    public void setOverdue(boolean overdue) { this.overdue = overdue; }

}
