package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.Attendance;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.service.EmailService;
import com.commit.collaboration_board_server.service.AttendanceManagementService;
import com.commit.collaboration_board_server.service.UserService;
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

    private final UserService userService;
    private final AttendanceManagementService AttendanceManagementService;
    private final EmailService emailService;

    @Autowired
    public EmailController(UserService userService, AttendanceManagementService AttendanceManagementService, EmailService emailService) {
        this.userService = userService;
        this.AttendanceManagementService = AttendanceManagementService;
        this.emailService = emailService;
    }

    @PostMapping("/sendCoreTimeWarnings")
    public ResponseEntity<String> sendCoreTimeWarnings(
            @RequestParam(defaultValue = "Default Subject") String subject,
            @RequestParam(defaultValue = "Default Text") String text) {
        try {
            List<Attendance> allWorkDates = AttendanceManagementService.getAllWorkDates();

            for (Attendance workDate : allWorkDates) {
                String userId = workDate.getUserId();
                String startTime = workDate.getStartTime();
                String endTime = workDate.getEndTime();

                // startTime과 endTime 사이에 코어 타임이 포함되지 않으면 이메일을 보내는 로직
                if (emailService.isCoreTime(startTime, endTime)) {
                    User user = userService.findByUserId(userId);
                    if (user != null && user.getEmail() != null) {
                        emailService.sendEmail(user.getEmail(), subject, text);
                        logger.info("Sent warning email to user ID: {}, email: {}", userId, user.getEmail());
                    } else {
                        logger.warn("User with ID {} has no valid email.", userId);
                    }
                }
            }

            return ResponseEntity.ok("Warning emails sent to users violating core time policy.");
        } catch (Exception e) {
            logger.error("Error sending core time warning emails.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send warning emails.");
        }
    }

}
