package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.AttendanceMapper;
import com.commit.collaboration_board_server.mapper.UserMapper;
import com.commit.collaboration_board_server.model.Attendance;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.util.ResponseStatusUtil;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.time.LocalTime;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceManagementService {

    private final AttendanceMapper attendanceMapper;
    private final EmailService emailService;
    private final UserMapper userMapper;


    public AttendanceManagementService(AttendanceMapper attendanceMapper, EmailService emailService, UserMapper userMapper) {
        this.attendanceMapper = attendanceMapper;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    public List<Attendance> getAllWorkDates() {
        return attendanceMapper.getAllWorkDates();
    }

    public Integer saveAttendanceOperation(Attendance attendance) {
        boolean isDuplicate = attendanceMapper.existsAttendanceByParams(attendance);
        if (isDuplicate) {
            return ResponseStatusUtil.CODES.keySet().stream()
                    .filter(key -> "Dupulicated saveAttendanceOperation Exception".equals(ResponseStatusUtil.CODES.get(key)))
                    .findFirst()
                    .orElse(10000);
        }



        // 근무 시작 시간을 기준으로 코어타임에 근무했는지 확인
        LocalDateTime startTime = attendance.getWorkStartTime(); // 예: "2024-12-29 09:00:00"

        // 코어타임을 기준으로 근무 시작 시간이 10:00 ~ 14:00 사이에 있지 않으면
        if (isCoreTime(startTime)) {
            User user =  userMapper.findByUserId(attendance.getUserId());
            String penaltyMessage = "Core time violation: worked outside core time (10:00 - 14:00)";
            emailService.sendEmail(user.getEmail(), penaltyMessage, penaltyMessage);

        }


        // 퇴근하지 않으면 저장 안되게
        boolean hasUnfinishedAttendance = attendanceMapper.existsUnfinishedAttendance(attendance.getUserId());
        if (!hasUnfinishedAttendance)
            attendanceMapper.insertAttendance(attendance);
        return ResponseStatusUtil.CODES_SUCCESS;
    }

    // 근무 시작 시간이 코어타임(10:00~14:00) 안에 있는지 확인
    private boolean isCoreTime(LocalDateTime startTime) {
        // 코어타임을 10:00~14:00으로 설정
        LocalTime coreTimeStart = LocalTime.of(10, 0); // 10:00
        LocalTime coreTimeEnd = LocalTime.of(14, 0);   // 14:00

        LocalTime startTimeOnly = startTime.toLocalTime();

        // startTime이 coreTimeStart와 coreTimeEnd 사이에 있는지 확인
        return !startTimeOnly.isBefore(coreTimeStart) && !startTimeOnly.isAfter(coreTimeEnd);
    }

    public void updateWorkEndTime(Attendance attendance) {
        attendance.setEndTime(LocalDateTime.now());
        attendanceMapper.updateWorkEndTime(attendance);
    }



    public Map<String, Object> getMonthlyAttendanceInfo(String userId, Date date) {
        Map<String, Object> result = new HashMap<>();

        int monthlyWorkHours = attendanceMapper.getMonthlyWorkHours(userId, date);
        result.put("monthlyWorkHours", monthlyWorkHours);

        int coreTimeViolations = attendanceMapper.getCoreTimeViolations(userId, date);
        result.put("coreTimeViolations", coreTimeViolations);

        int penaltyPoints = coreTimeViolations / 3;
        result.put("penaltyPoints", penaltyPoints);

        if (penaltyPoints >= 3) {
            result.put("penaltyAction", "Vacation day deducted and written explanation required.");
        } else {
            result.put("penaltyAction", "No penalty.");
        }

        return result;
    }
}