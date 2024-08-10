package com.redsur.library.controllers;

import com.redsur.library.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.redsur.library.services.AuthService;
import com.redsur.library.services.JwtUtilService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDetailsService userDetailsService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        System.out.println("Received registration request for user: " + user.getUsername());
        ResponseEntity<?> response = authService.registerUser(user);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }  else {
            return response;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails =this.userDetailsService.loadUserByUsername(user.getUsername());

            String jwt = this.jwtUtilService.generateToken(userDetails);

            User authenticatedUser = authService.loginUser(user.getUsername(), user.getPassword());
            return new ResponseEntity<>(Collections.singletonMap("token", jwt), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Authentication Error"));
        }
    }
}