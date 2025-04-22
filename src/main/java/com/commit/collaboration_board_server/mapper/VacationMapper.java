package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Vacation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VacationMapper {

    // 휴가 신청
    int insertVacation(Vacation vacation);

    // 휴가 목록 조회 (개인/전체)
    List<Vacation> selectVacationsByUserNo(int userNo);
    List<Vacation> selectAllVacations();

    // 특정 휴가 상세 조회
    Vacation selectVacationById(int id);

    // 승인/반려 처리
    int updateVacationStatus(@Param("id") int id, @Param("status") String status);

    // 남은 휴가 차감
    int decrementVacationCount(@Param("userNo") int userNo, @Param("days") int days);
}
