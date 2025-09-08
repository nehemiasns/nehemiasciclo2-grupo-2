
package com.example.library.models;

import java.util.UUID;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private int year;
    private int copies;

    public Book() {}

    public Book(String title, String author, String category, int year, int copies) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.copies = copies;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public int getCopies() { return copies; }

    public void setTitle(String t) { this.title = t; }
    public void setAuthor(String a) { this.author = a; }
    public void setCategory(String c) { this.category = c; }
    public void setYear(int y) { this.year = y; }
    public void setCopies(int c) { this.copies = c; }
}
