package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByBClass_Id(Long id);
    List<Attendance> findAllByStudent_IdAndBClass_Id(Long studentId, Long bClassId);
}
