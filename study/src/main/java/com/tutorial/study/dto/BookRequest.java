package com.tutorial.study.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookRequest {

    @NotBlank(message = "Title is required!")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "Author is required!")
    private String author;

    public BookRequest(){
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
}
