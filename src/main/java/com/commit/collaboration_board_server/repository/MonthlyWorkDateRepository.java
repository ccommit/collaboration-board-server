package com.commit.collaboration_board_server.repository;

import com.commit.collaboration_board_server.model.MonthlyWorkDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyWorkDateRepository extends JpaRepository<MonthlyWorkDate, Long> {

    List<MonthlyWorkDate> findByTotalWorkTimeLessThan(int hours);
}
