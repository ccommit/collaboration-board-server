package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ScheduleMapper;
import com.commit.collaboration_board_server.model.Schedule;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private JavaMailSender mailSender;
    private final ScheduleMapper scheduleMapper;

    public ScheduleService(JavaMailSender mailSender, ScheduleMapper scheduleMapper) {
        this.mailSender = mailSender;
        this.scheduleMapper = scheduleMapper;
    }


    @Scheduled(cron = "0 0 0 * * MON")
    public void sendWeeklyInvitations() {
        List<Schedule> schedules = scheduleMapper.getWeeklySchedules();
        for (Schedule schedule : schedules) {
            sendEmail(schedule);
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void sendMonthlyInvitations() {
        List<Schedule> schedules = scheduleMapper.getMonthlySchedules();
        for (Schedule schedule : schedules) {
            sendEmail(schedule);
        }
    }


    private void sendEmail(Schedule schedule) {
        String[] emailList = schedule.getInvitedEmails().split(",");

        for (String email : emailList) {
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
                e.printStackTrace();
            }
        }
    }





    public void createSchedule(Schedule schedule) {
        scheduleMapper.insertschedule(schedule);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleMapper.findscheduleById(id);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleMapper.findAllschedules();
    }


    public void updateSchedule(Long id, Schedule updatedschedule) {
        Schedule existingschedule = scheduleMapper.findscheduleById(id);
        if (existingschedule != null) {
            updatedschedule.setId(id);
            scheduleMapper.updateschedule(updatedschedule);
        }
    }

    public void deleteSchedule(Long id) {
        scheduleMapper.deleteschedule(id);
    }
}
