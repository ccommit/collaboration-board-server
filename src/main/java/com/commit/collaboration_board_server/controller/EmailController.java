package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.repository.UserRepository;
import com.commit.collaboration_board_server.service.EmailService;
import com.commit.collaboration_board_server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public EmailController(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String userId,
                                            @RequestParam String subject,
                                            @RequestParam String text) {
        try {
            // 사용자 정보 조회
            User user = userRepository.findByUserId(userId);

            if (user == null) {
                logger.warn("User with ID {} not found.", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            String email = user.getEmail();
            logger.info("Sending email to userID: {}, email: {}", userId, email);

            // 이메일 발송
            emailService.sendEmail(email, subject, text);
            logger.info("Email sent successfully to {}", email);

            return ResponseEntity.ok("Email sent successfully");

        } catch (Exception e) {
            logger.error("Error sending email to userID: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}