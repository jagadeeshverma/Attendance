package com.example.attendance.repository;

import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserAndLoginTimeBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    List<Attendance> findByUserAndLogoutTimeIsNull(User user);
}
