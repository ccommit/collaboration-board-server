package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.Schedule;
import com.commit.collaboration_board_server.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody Schedule schedule) {
        scheduleService.createSchedule(schedule);
        return ResponseEntity.ok("schedule created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getscheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return (schedule != null) ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllschedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }
}
