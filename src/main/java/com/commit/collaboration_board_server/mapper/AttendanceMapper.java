package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {
    void insertAttendance(Attendance attendance);

    void updateWorkEndTime(Attendance attendance);
    // 월간 근로 시간 조회
    int getMonthlyWorkHours(@Param("userNo") long userNo, @Param("date") Date date);
    // 코어타임 위반 횟수 조회
    int getCoreTimeViolations(@Param("userNo") long userNo, @Param("date") Date date);

    List<Attendance> getAllWorkDates();

    boolean existsAttendanceByParams(Attendance attendance);

    boolean existsUnfinishedAttendance(@Param("userNo") long userNo);

}

