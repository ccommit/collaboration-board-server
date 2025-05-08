package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.MonthlyWorkDate;
import com.commit.collaboration_board_server.model.Schedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScheduleMapper {
    void insertSchedule(Schedule schedule);

    Schedule findScheduleById(Long id);

    List<Schedule> findAllSchedules();

    void updateSchedule(Schedule schedule);

    void deleteSchedule(Long id);

    List<Schedule> getWeeklySchedules();

    List<Schedule> getMonthlySchedules();

    List<Schedule> getSchedulesByType(@Param("regularType") String regularType);
}
