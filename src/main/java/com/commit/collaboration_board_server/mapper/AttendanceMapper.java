package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Attendance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface AttendanceMapper {

    // 월간 근로 시간 조회
    int getMonthlyWorkHours(@Param("userId") String userId, @Param("date") Date date);

    // 코어타임 위반 횟수 조회
    int getCoreTimeViolations(@Param("userId") String userId, @Param("date") Date date);

    @Insert("INSERT INTO DAILY_WORK_DATE (user_id, work_date, work_start_time, work_end_time, work_time) " +
            "VALUES (#{userId}, #{workDate}, #{workStartTime}, #{workEndTime}, #{workTime})")
    void insertAttendance(Attendance attendance);
}