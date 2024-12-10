package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest, HttpServletRequest request) {
        User user = userService.findByUserId(loginRequest.getUserId());

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("Invalid userId or password.");
        }

        // 세션 생성 및 저장
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);

        return ResponseEntity.ok("Login successful.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return ResponseEntity.ok("Logout successful.");
    }
}