package com.commit.collaboration_board_server.controller;
import com.commit.collaboration_board_server.model.VacationCategory;
import com.commit.collaboration_board_server.model.Vacation;
import com.commit.collaboration_board_server.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    // 1. 휴가 신청
    @PostMapping
    public String applyVacation(@RequestBody Vacation vacation) {
        vacationService.applyVacation(vacation);
        return "휴가 신청이 완료되었습니다.";
    }

    // 2. 관리자: 휴가 승인
    @PostMapping("/{vacationId}/approve")
    public String approveVacation(@PathVariable int vacationId) {
        vacationService.approveVacation(vacationId);
        return "휴가가 승인되었습니다.";
    }

    // 3. 관리자: 휴가 반려
    @PostMapping("/{vacationId}/reject")
    public String rejectVacation(@PathVariable int vacationId) {
        vacationService.rejectVacation(vacationId);
        return "휴가가 반려되었습니다.";
    }



    // 5. 관리자: 전체 휴가 목록 조회
    @GetMapping
    public List<Vacation> getAllVacations() {
        return vacationService.getAllVacations();
    }
}
