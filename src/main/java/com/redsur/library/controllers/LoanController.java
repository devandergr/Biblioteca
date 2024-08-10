package com.redsur.library.controllers;

import com.redsur.library.models.LoanDTO;
import com.redsur.library.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> createLoan(
            @RequestParam("username") String username,
            @RequestParam("bookId") Integer bookId) {
        try {
            loanService.createLoan(username, bookId);
            return ResponseEntity.ok(Map.of("message", "Loan created successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Error creating loan: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLoan(@PathVariable("id") Integer id) {
        System.out.println("Deleting loan with id: " + id); // Verifica el valor del id
        try {
            loanService.deleteLoan(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/active-users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<LoanDTO> getUsersWithActiveLoans() {
        return loanService.getUsersWithActiveLoans();
    }

    @GetMapping("/my-loans")
    public ResponseEntity<List<LoanDTO>> getUserLoans(@RequestParam("username") String username) {
        try {
            List<LoanDTO> loans = loanService.getUserLoans(username);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
