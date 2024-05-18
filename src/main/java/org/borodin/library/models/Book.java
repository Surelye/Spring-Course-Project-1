package org.borodin.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int bookId;
    private Integer personId;

    @NotEmpty(message = "Book title should not be empty")
    @Size(min = 1, max = 50, message = "Book title should be between 1 and 50 characters")
    private String bookTitle;

    @NotEmpty(message = "Author of the book should not be empty")
    @Size(min = 3, max = 50, message = "Author of the book should be between 3 and 50 characters")
    private String bookAuthor;

    @Min(value = 1500, message = "Year of publication should be greater of equal than 1500")
    private int publicationYear;

    public Book() {}

    public Book(int bookId, Integer personId, String bookTitle, String bookAuthor, int publicationYear) {
        this.bookId = bookId;
        this.personId = personId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.publicationYear = publicationYear;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
