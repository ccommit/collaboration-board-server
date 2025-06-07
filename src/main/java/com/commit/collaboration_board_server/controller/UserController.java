package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.dto.request.UserCreateRequest;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.service.UserService;
import com.commit.collaboration_board_server.util.ResponseStatusUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest, HttpSession session) {
        boolean isAuthenticated = userService.authenticate(loginRequest);

        if (!isAuthenticated) {
            return ResponseEntity.status(ResponseStatusUtil.getStatus("UNAUTHORIZED")).body("Invalid userId or password.");
        }

        userService.saveUserSession(session, loginRequest);
        return ResponseEntity.status(ResponseStatusUtil.getStatus("SUCCESS")).body("Login successful.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.removeUserSession(request);
        return ResponseEntity.status(ResponseStatusUtil.getStatus("SUCCESS")).body("Logout successful.");
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) Long userNo) {
        return userService.getUsers(userNo);
    }


    @PostMapping
    public void createUser(@RequestBody UserCreateRequest user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserNo(id);
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}