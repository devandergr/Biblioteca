package com.redsur.library.models;

import java.time.LocalDateTime;

public class LoanDTO {

    private Integer id;
    private String username;
    private String bookTitle;
    private LocalDateTime dateLoan;
    private LocalDateTime dateReturn;
    private Boolean status;
    private Integer bookId;

    public LoanDTO(Integer id, String username, String bookTitle, LocalDateTime dateLoan, LocalDateTime dateReturn, Boolean status, Integer bookId) {
        this.id = id;
        this.username = username;
        this.bookTitle = bookTitle;
        this.dateLoan = dateLoan;
        this.dateReturn = dateReturn;
        this.status = status;
        this.bookId = bookId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getDateLoan() {
        return dateLoan;
    }

    public void setDateLoan(LocalDateTime dateLoan) {
        this.dateLoan = dateLoan;
    }

    public LocalDateTime getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDateTime dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}


