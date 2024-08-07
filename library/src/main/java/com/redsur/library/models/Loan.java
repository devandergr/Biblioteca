package com.redsur.library.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;


    private LocalDateTime date_loan;
    private LocalDateTime date_return;
    private Boolean status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getDate_loan() {
        return date_loan;
    }

    public void setDate_loan(LocalDateTime date_loan) {
        this.date_loan = date_loan;
    }

    public LocalDateTime getDate_return() {
        return date_return;
    }

    public void setDate_return(LocalDateTime date_return) {
        this.date_return = date_return;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
