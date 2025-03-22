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
    public ResponseEntity<String> createschedule(@RequestBody Schedule schedule) {
        scheduleService.createSchedule(schedule);
        return ResponseEntity.ok("schedule created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getscheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getscheduleById(id);
        return (schedule != null) ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllschedules() {
        List<Schedule> schedules = scheduleService.getAllschedules();
        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateschedule(@PathVariable Long id, @RequestBody Schedule updatedschedule) {
        scheduleService.updateschedule(id, updatedschedule);
        return ResponseEntity.ok("schedule updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteschedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("schedule deleted successfully");
    }
}
