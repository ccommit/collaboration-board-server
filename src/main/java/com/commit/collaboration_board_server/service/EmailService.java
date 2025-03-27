package com.commit.collaboration_board_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public static boolean isCoreTime(LocalDateTime startTime, LocalDateTime endTime) {
        // 코어 타임 시작 및 끝
        LocalTime coreStart = LocalTime.of(10, 0);  // 오전 10:00
        LocalTime coreEnd = LocalTime.of(14, 0);    // 오후 2:00

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 시간 형식 지정 (년-월-일 시:분:초)

            // startTime과 endTime을 LocalDateTime으로 변환
            LocalTime start = startTime.toLocalTime();
            LocalTime end = endTime.toLocalTime();


            // 코어 타임이 startTime과 endTime 사이에 완전히 포함되어 있지 않은 경우 true 반환
            // 즉, startTime이 coreStart보다 늦거나 endTime이 coreEnd보다 빠른 경우 true
            return !(start.isBefore(coreStart) && end.isAfter(coreEnd));
        } catch (Exception e) {
            System.out.println("Invalid time format: startTime=" + startTime + ", endTime=" + endTime);
            return true;  // 잘못된 형식이라면 기본적으로 이메일을 보냄
        }
    }

}
