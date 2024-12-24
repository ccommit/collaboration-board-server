package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.AttendanceMapper;
import com.commit.collaboration_board_server.model.Attendance;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceManagementService {

    private final AttendanceMapper attendanceMapper;

    public AttendanceManagementService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }
    public void saveAttendance(Attendance attendance) {
        attendanceMapper.insertAttendance(attendance);
    }

    public Map<String, Object> getMonthlyAttendanceInfo(String userId, Date date) {
        Map<String, Object> result = new HashMap<>();

        // 1. 월간 근로 시간 조회
        int monthlyWorkHours = attendanceMapper.getMonthlyWorkHours(userId, date);
        result.put("monthlyWorkHours", monthlyWorkHours);

        // 2. 코어타임 위반 횟수 조회
        int coreTimeViolations = attendanceMapper.getCoreTimeViolations(userId, date);
        result.put("coreTimeViolations", coreTimeViolations);

        // 3. 벌점 계산
        int penaltyPoints = coreTimeViolations / 3; // 3회 이상이면 1점
        result.put("penaltyPoints", penaltyPoints);

        // 4. 알림 및 패널티 처리
        if (penaltyPoints >= 3) {
            result.put("penaltyAction", "Vacation day deducted and written explanation required.");
        } else {
            result.put("penaltyAction", "No penalty.");
        }

        return result;
    }
}