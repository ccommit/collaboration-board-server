package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.aspect.CheckLoginStatus;
import com.commit.collaboration_board_server.service.AttendanceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("attendances")
public class AttendanceController {

    private final AttendanceManagementService attendanceManagementService;

    @Autowired
    public AttendanceController(AttendanceManagementService attendanceManagementService) {
        this.attendanceManagementService = attendanceManagementService;
    }

    @CheckLoginStatus
    @GetMapping("/protected-resource")
    public String accessProtectedResource() {
        return "Access granted to protected resource!";
    }

//TODO:   일반 사용자만 접근 가능
//    @CheckLoginStatus(userType = UserType.USER)
//    @PostMapping("/start")
//    public ResponseEntity<String> submitAttendance(@RequestBody Attendance attendance) {
//        attendanceManagementService.saveAttendance(attendance);
//        return ResponseEntity.ok("Attendance submitted successfully.");
//    }

//TODO:    // 관리자 권한 필요
//    @CheckLoginStatus(userType = UserType.ADMIN)
//    @GetMapping("/admin-report")
//    public ResponseEntity<String> getAdminReport() {
//        // 관리자 전용 로직
//        return ResponseEntity.ok("Admin report generated.");
//    }
}
