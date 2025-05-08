package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ScheduleMapper;
import com.commit.collaboration_board_server.model.Schedule;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    private final JavaMailSender mailSender;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(JavaMailSender mailSender, ScheduleMapper scheduleMapper) {
        this.mailSender = mailSender;
        this.scheduleMapper = scheduleMapper;
    }

    @Scheduled(cron = "0 0 9 * * *") // 매일 오전 9시 실행
    public void sendDailyInvitations() {
        logger.info("Schedule triggered at: {} " ,LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        sendInvitationsByType("일간");
    }

    @Scheduled(cron = "0 0 9 * * MON") // 매주 월요일 오전 9시 실행
    public void sendWeeklyInvitations() {
        sendInvitationsByType("주간");
    }

    @Scheduled(cron = "0 0 9 1 * *") // 매월 1일 오전 9시 실행
    public void sendMonthlyInvitations() {
        sendInvitationsByType("월간");
    }

    private void sendInvitationsByType(String regularType) {
        List<Schedule> schedules = scheduleMapper.getSchedulesByType(regularType);
        for (Schedule schedule : schedules) {
            sendEmail(schedule);
        }
    }

    private void sendEmail(Schedule schedule) {
        List<String> invitedEmails = schedule.getInvitedEmails();
        if (invitedEmails == null || invitedEmails.isEmpty()) {
            logger.info("No invited emails for schedule: {} " ,schedule.getTitle());
            return;
        }
        for (String email : invitedEmails) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email.trim());
                helper.setSubject("[일정 알림] " + schedule.getTitle());
                helper.setText(
                        "안녕하세요,\n\n" +
                                "일정 상세 내용: " + schedule.getDescription() + "\n" +
                                "시작 시간: " + schedule.getStartDate() + "\n" +
                                "종료 시간: " + schedule.getEndDate() + "\n\n" +
                                "감사합니다.",
                        true
                );
                mailSender.send(message);
            } catch (MessagingException e) {
                logger.warn("Email sending failed for {} : {} " ,email,e.getMessage());
                e.printStackTrace();
            }
        }
    }





    public void createSchedule(Schedule schedule) {
        scheduleMapper.insertSchedule(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleMapper.findScheduleById(id);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleMapper.findAllSchedules();
    }


    public void updateSchedule(Long id, Schedule updatedschedule) {
        Schedule existingschedule = scheduleMapper.findScheduleById(id);
        if (existingschedule != null) {
            updatedschedule.setUserNo(id);
            scheduleMapper.updateSchedule(updatedschedule);
        }
    }

    public void deleteSchedule(Long id) {
        scheduleMapper.deleteSchedule(id);
    }
}
