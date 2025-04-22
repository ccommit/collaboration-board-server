package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.VacationMapper;
import com.commit.collaboration_board_server.model.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationMapper vacationMapper;

    public void applyVacation(Vacation vacation) {
        // startTime ~ endTime 일수 계산 (휴가 일수 확인용, DB 반영은 안 함)
        long days = ChronoUnit.DAYS.between(vacation.getStartTime(), vacation.getEndTime()) + 1;

        // 상태는 강제 세팅
        vacation.setStatus("PENDING");

        vacationMapper.insertVacation(vacation);
    }

    public void approveVacation(int vacationId) {
        Vacation vacation = vacationMapper.selectVacationById(vacationId);
        int days = (int) ChronoUnit.DAYS.between(vacation.getStartTime(), vacation.getEndTime()) + 1;

        vacationMapper.updateVacationStatus(vacationId, "APPROVED");
        vacationMapper.decrementVacationCount(vacation.getUserNo(), days);
    }

    public void rejectVacation(int vacationId) {
        vacationMapper.updateVacationStatus(vacationId, "REJECTED");
    }

    public List<Vacation> getUserVacations(int userNo) {
        return vacationMapper.selectVacationsByUserNo(userNo);
    }

    public List<Vacation> getAllVacations() {
        return vacationMapper.selectAllVacations();
    }
}