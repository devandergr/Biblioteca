package com.redsur.library.controllers;

import com.redsur.library.models.User;
import com.redsur.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/details")
    public User getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return userService.findByUsername(username);
    }

    @GetMapping("/my-loads")
    public ResponseEntity<List<Map<String, Object>>> getBooksLoans(@RequestParam("username") String username) {
        try {
            List<Map<String, Object>> activeUsers = userService.getBooksLoads(username);
            return ResponseEntity.ok(activeUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("username") String username,
                                                 @RequestParam("newPassword") String newPassword) {
        try {
            userService.changePassword(username, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error changing password: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User Not Found");
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
