package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ScheduleMapper;
import com.commit.collaboration_board_server.model.MonthlyWorkDate;
import com.commit.collaboration_board_server.model.Schedule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }


    public void createSchedule(Schedule schedule) {
        scheduleMapper.insertschedule(schedule);
    }

    public Schedule getscheduleById(Long id) {
        return scheduleMapper.findscheduleById(id);
    }

    public List<Schedule> getAllschedules() {
        return scheduleMapper.findAllschedules();
    }

    @Transactional
    public void updateschedule(Long id, Schedule updatedschedule) {
        Schedule existingschedule = scheduleMapper.findscheduleById(id);
        if (existingschedule != null) {
            updatedschedule.setId(id);
            scheduleMapper.updateschedule(updatedschedule);
        }
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleMapper.deleteschedule(id);
    }
}
