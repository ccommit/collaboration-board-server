package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.dto.request.VacationRequest;
import com.commit.collaboration_board_server.dto.response.VacationResponse;
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

    public Vacation applyVacation(VacationRequest request) {
        // startTime ~ endTime 일수 계산 (휴가 일수 확인용, DB 반영은 안 함)
        Vacation vacation = Vacation.builder()
                .userNo(request.getUserNo())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .vacationCategoryId(request.getVacationCategoryId())
                .status(VacationStatus.PENDING)
                .build();
        long days = ChronoUnit.DAYS.between(vacation.getStartTime(), vacation.getEndTime()) + 1;

        // 상태는 강제 세팅
        vacation.setStatus(VacationStatus.PENDING);
        vacationMapper.insertVacation(vacation);
        return vacation;
    }

    public VacationResponse toResponse(Vacation vacation) {
        return VacationResponse.builder()
                .id(vacation.getId())
                .userNo(vacation.getUserNo())
                .startTime(vacation.getStartTime())
                .endTime(vacation.getEndTime())
                .vacationCategoryId(vacation.getVacationCategoryId())
                .status(vacation.getStatus())
                .build();
    }


    public void approveVacation(int vacationId) {
        Vacation vacation = vacationMapper.selectVacationById(vacationId);
        int days = (int) ChronoUnit.DAYS.between(vacation.getStartTime(), vacation.getEndTime()) + 1;

        vacationMapper.updateVacationStatus(vacationId, VacationStatus.APPROVED.name());
        vacationMapper.decrementVacationCount(vacation.getUserNo(), days);
    }

    public void rejectVacation(int vacationId) {
        vacationMapper.updateVacationStatus(vacationId, VacationStatus.REJECTED.name());
    }

    public List<Vacation> getUserVacations(int userNo) {
        return vacationMapper.selectVacationsByUserNo(userNo);
    }

    public List<Vacation> getAllVacations() {
        return vacationMapper.selectAllVacations();
    }
}