package com.rita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import com.rita.service.UserService;
import com.rita.model.User;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userInput) {
        try {
            User saved = userService.registerUser(userInput);
            User result = new User();
            result.setId(saved.getId());
            result.setUsername(saved.getUsername());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    // Login a user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userInput, HttpSession session) {
        User user = userService.login(userInput.getUsername(), userInput.getPassword());
        if (user != null) {
            session.setAttribute("loginUser", user.getUsername());

            Map<String, Object> result = new HashMap<>();
            result.put("username", user.getUsername());
            result.put("status", "ok");
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // Get user session status
    @GetMapping("/check")
    public ResponseEntity<?> checkLogin(HttpSession session) {
        String username = (String) session.getAttribute("loginUser");
        if (username != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("username", username);
            result.put("loggedIn", true);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }

    // Logout a user
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}