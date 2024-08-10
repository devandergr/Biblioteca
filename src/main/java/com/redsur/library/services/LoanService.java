package com.redsur.library.services;

import com.redsur.library.models.Book;
import com.redsur.library.models.Loan;
import com.redsur.library.models.LoanDTO;
import com.redsur.library.models.User;
import com.redsur.library.repositories.BookRepository;
import com.redsur.library.repositories.LoanRepository;
import com.redsur.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void createLoan(String username, Integer bookId) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new Exception("Book not found");
        }

        // Check availability
        if (book.getAvailable() <= 0 ) {
            throw new Exception("Book is not available");
        }

        // Create loan
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setDate_loan(LocalDateTime.now());
        loan.setStatus(true); // True for loaned

        loanRepository.save(loan);

        // Update book availability
        book.setAvailable(book.getAvailable() - 1);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteLoan(Integer id) {
        Optional<Loan> loanOptional = loanRepository.findById(id);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();

            // Actualizar el campo available del libro
            Book book = loan.getBook();
            book.setAvailable(book.getAvailable() + 1); // Incrementar el número de copias disponibles
            bookRepository.save(book);

            // Eliminar el préstamo
            loanRepository.delete(loan);
        } else {
            throw new RuntimeException("Loan not found with id: " + id);
        }
    }

    public List<LoanDTO> getUsersWithActiveLoans() {
        List<Loan> activeLoans = loanRepository.findAllByStatus(true);
        return activeLoans.stream()
                .map(loan -> new LoanDTO(
                        loan.getId(),
                        loan.getUser().getUsername(),
                        loan.getBook().getTitle(),
                        loan.getDate_loan(),
                        loan.getDate_return(),
                        loan.getStatus(),
                        loan.getBook().getId()))
                .collect(Collectors.toList());
    }

    public List<LoanDTO> getUserLoans(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Loan> userLoans = loanRepository.findByUserAndStatus(user, true);
        return userLoans.stream()
                .map(loan -> new LoanDTO(
                        loan.getId(),
                        loan.getUser().getUsername(),
                        loan.getBook().getTitle(),
                        loan.getDate_loan(),
                        loan.getDate_return(),
                        loan.getStatus(),
                        loan.getBook().getId()))
                .collect(Collectors.toList());
    }
}


