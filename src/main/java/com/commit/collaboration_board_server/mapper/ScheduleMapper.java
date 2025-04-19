package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.MonthlyWorkDate;
import com.commit.collaboration_board_server.model.Schedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    void insertSchedule(Schedule schedule);

    @Select("SELECT * FROM schedule WHERE id = #{id}")
    Schedule findScheduleById(Long id);

    @Select("SELECT * FROM schedule")
    List<Schedule> findAllSchedules();

    @Update("UPDATE schedule SET user_id = #{userNo}, email = #{email}, title = #{title}, schedule_category = #{scheduleCategory}, " +
            "description = #{description}, start_date = #{startDate}, end_date = #{endDate}, updated_at = NOW() WHERE id = #{id}")
    void updateSchedule(Schedule schedule);

    @Delete("DELETE FROM schedule WHERE id = #{id}")
    void deleteSchedule(Long id);

    @Select("SELECT * FROM monthly_work_date WHERE total_work_time < #{hours}")
    List<MonthlyWorkDate> findByTotalWorkTimeLessThan(int hours);


    // 주간 일정 조회 (현재 시간 이후 일정)
    @Select("SELECT * FROM schedule WHERE schedule_category = 'weekly' AND start_date > NOW()")
    List<Schedule> getWeeklySchedules();

    // 월간 일정 조회 (현재 시간 이후 일정)
    @Select("SELECT * FROM schedule WHERE schedule_category = 'monthly' AND start_date > NOW()")
    List<Schedule> getMonthlySchedules();

    List<Schedule> getSchedulesByType(@Param("regularType") String regularType);
}
