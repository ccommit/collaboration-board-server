package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.dto.request.VacationRequest;
import com.commit.collaboration_board_server.dto.response.VacationResponse;
import com.commit.collaboration_board_server.mapper.VacationMapper;
import com.commit.collaboration_board_server.model.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationMapper vacationMapper;

    public Vacation applyVacation(VacationRequest request) {
        // startTime ~ endTime 일수 계산 (휴가 일수 확인용, DB 반영은 안 함)
        validateRequest(request);
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

    private void validateRequest(VacationRequest request) {
        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime();
        int category = request.getVacationCategoryId();

        switch (category) {
            case 1: // 연차
                if (!start.isBefore(end)) {
                    throw new IllegalArgumentException("연차는 시작일이 종료일보다 앞서야 합니다.");
                }
                break;

            case 2: // 오전 반차 (09:00 ~ 13:30)
                if (!isValidHalfDay(start, end, LocalTime.of(9, 0), LocalTime.of(13, 30))) {
                    throw new IllegalArgumentException("오전 반차는 09:00~13:30 사이여야 합니다.");
                }
                break;

            case 3: // 오후 반차 (13:30 ~ 18:00)
                if (!isValidHalfDay(start, end, LocalTime.of(13, 30), LocalTime.of(18, 0))) {
                    throw new IllegalArgumentException("오후 반차는 13:30~18:00 사이여야 합니다.");
                }
                break;

            default:
                throw new IllegalArgumentException("유효하지 않은 휴가 유형입니다.");
        }
    }

    private boolean isValidHalfDay(LocalDateTime start, LocalDateTime end, LocalTime expectedStart, LocalTime expectedEnd) {
        return start.toLocalTime().equals(expectedStart)
                && end.toLocalTime().equals(expectedEnd)
                && start.toLocalDate().equals(end.toLocalDate());
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