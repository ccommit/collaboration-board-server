package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.MonthlyWorkDate;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.repository.MonthlyWorkDateRepository;
import com.commit.collaboration_board_server.repository.UserRepository;
import com.commit.collaboration_board_server.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final UserRepository userRepository;
    private final MonthlyWorkDateRepository monthlyWorkDateRepository;
    private final EmailService emailService;

    @Autowired
    public EmailController(UserRepository userRepository,
                           MonthlyWorkDateRepository monthlyWorkDateRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.monthlyWorkDateRepository = monthlyWorkDateRepository;
        this.emailService = emailService;
    }

    @PostMapping("/sendToLowWorkTimeUsers")
    public ResponseEntity<String> sendEmailToLowWorkTimeUsers(@RequestParam String subject, @RequestParam String text) {
        try {
            // 160시간 미만일 경우 이메일 발송
            List<MonthlyWorkDate> lowWorkTimeUsers = monthlyWorkDateRepository.findByTotalWorkTimeLessThan(160);

            if (lowWorkTimeUsers.isEmpty()) {
                logger.info("No users found with total work time less than 160 hours.");
                return ResponseEntity.ok("No users found to send emails.");
            }

            for (MonthlyWorkDate workDate : lowWorkTimeUsers) {
                String userId = workDate.getUserId();
                User user = userRepository.findByUserId(userId);

                if (user != null && user.getEmail() != null) {
                    String email = user.getEmail();
                    //logger.info("Sending email to userID: {}, email: {}", userId, email);

                    // 이메일 발송
                    emailService.sendEmail(email, subject, text);
                    //logger.info("Email sent successfully to {}", email);
                } else {
                    logger.warn("User with ID {} does not have a valid email.", userId);
                }
            }

            return ResponseEntity.ok("Emails sent to users with total work time less than 160 hours.");

        } catch (Exception e) {
            logger.error("Error sending emails to users with low work time.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send emails.");
        }
    }
}