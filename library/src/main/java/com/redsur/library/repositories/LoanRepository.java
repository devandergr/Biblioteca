package com.redsur.library.repositories;

import com.redsur.library.models.Book;
import com.redsur.library.models.Loan;
import com.redsur.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Loan findTopByUserAndBookAndStatus(User user, Book book, Boolean status);

    List<Loan> findAllByStatus(Boolean status);

    List<Loan> findByUserAndStatus(User user, Boolean status);

}
