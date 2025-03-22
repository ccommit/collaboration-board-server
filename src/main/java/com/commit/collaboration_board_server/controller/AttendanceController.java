package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.aspect.CheckLoginStatus;
import com.commit.collaboration_board_server.aspect.UserType;
import com.commit.collaboration_board_server.model.Attendance;
import com.commit.collaboration_board_server.service.AttendanceManagementService;
import com.commit.collaboration_board_server.util.ResponseStatusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendances")
public class AttendanceController {

    private final AttendanceManagementService attendanceManagementService;

    @Autowired
    public AttendanceController(AttendanceManagementService attendanceManagementService) {
        this.attendanceManagementService = attendanceManagementService;
    }

    @CheckLoginStatus(userType = UserType.USER)
    @GetMapping("/protected-resource")
    public String accessProtectedResource() {
        return "Access granted to protected resource!";
    }


    @CheckLoginStatus(userType = UserType.USER)
    @PostMapping("/start")
    public ResponseEntity<String> startUser(@RequestBody Attendance attendance) {
        Integer statsCode = attendanceManagementService.saveAttendanceOperation(attendance);
        if (statsCode != ResponseStatusUtil.CODES_SUCCESS) {

            return ResponseEntity
                    .status(200)
                    .body(ResponseStatusUtil.getCodes(statsCode));
        }

        // 정상적인 경우 200 상태와 성공 메시지 반환
        return ResponseEntity
                .status(ResponseStatusUtil.getStatus("SUCCESS"))
                .body("출석체크가 성공적으로 완료되었습니다.");
    }

    @CheckLoginStatus(userType = UserType.USER)
    @PutMapping("/end")
    public ResponseEntity<String> endWork(@RequestBody Attendance attendance) {
        attendanceManagementService.updateWorkEndTime(attendance);
        return ResponseEntity.status(ResponseStatusUtil.getStatus("SUCCESS")).body("work end time updated.");
    }



//TODO:    // 관리자 권한 필요
//    @CheckLoginStatus(userType = UserType.ADMIN)
//    @GetMapping("/admin-report")
//    public ResponseEntity<String> getAdminReport() {
//        // 관리자 전용 로직
//        return ResponseEntity.ok("Admin report generated.");
//    }
}
