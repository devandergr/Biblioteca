package com.redsur.library.services;

import com.redsur.library.models.Loan;
import com.redsur.library.models.User;
import com.redsur.library.repositories.LoanRepository;
import com.redsur.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public List<Map<String, Object>> getBooksLoads(String username) throws IllegalAccessException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalAccessException("User Not Found");
        }
        List<Loan> activeLoans = loanRepository.findByUserAndStatus(user, true);

        return activeLoans.stream()
                .collect(Collectors.groupingBy(
                        loan -> loan.getUser().getUsername(),
                        Collectors.mapping(
                                loan -> loan.getBook().getTitle(),
                                Collectors.toList()
                        )
                ))
                .entrySet().stream()
                .map(entry -> Map.of(
                        "username", entry.getKey(),
                        "books", entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void changePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
