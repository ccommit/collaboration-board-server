package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Attendance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    void insertAttendance(Attendance attendance);

    //코어타임 체크 하는 내용 추가
    void insertAttendanceOperation(@Param("Id") Long Id, @Param("userId") String userId, @Param("workCoreTime") String workCoreTime,
                                   @Param("penaltyMessage") String penaltyMessage);

    void updateWorkEndTime(Attendance attendance);

    // 월간 근로 시간 조회
    int getMonthlyWorkHours(@Param("userId") String userId, @Param("date") Date date);

    // 코어타임 위반 횟수 조회
    int getCoreTimeViolations(@Param("userId") String userId, @Param("date") Date date);


    List<Attendance> getAllWorkDates();

    void insertAttendanceOperation(String userId, String startTime, String penaltyMessage);

    boolean existsAttendanceByParams(Attendance attendance);

    boolean existsUnfinishedAttendance(@Param("userId") String userId);
}

