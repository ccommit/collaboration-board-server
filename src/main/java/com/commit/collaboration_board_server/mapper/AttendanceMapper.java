package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Attendance;
import com.commit.collaboration_board_server.model.Schedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    void insertAttendance(Attendance attendance);

    void updateWorkEndTime(Attendance attendance);

    // 월간 근로 시간 조회
    int getMonthlyWorkHours(@Param("userId") String userId, @Param("date") Date date);

    // 코어타임 위반 횟수 조회
    int getCoreTimeViolations(@Param("userId") String userId, @Param("date") Date date);


    List<Attendance> getAllWorkDates();

    boolean existsAttendanceByParams(Attendance attendance);

    boolean existsUnfinishedAttendance(@Param("userId") String userId);

    // 특정 regularType(일간, 주간, 월간)에 해당하는 일정 조회 (현재 시간 이후 일정만)


}

