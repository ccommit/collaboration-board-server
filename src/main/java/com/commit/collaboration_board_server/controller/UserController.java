package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.aspect.CheckLoginStatus;
import com.commit.collaboration_board_server.aspect.UserType;
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

    // 모든 사용자 조회
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 특정 사용자 조회
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 사용자 생성

    @PostMapping
    @CheckLoginStatus(userType = UserType.ADMIN)
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    // 사용자 수정
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}