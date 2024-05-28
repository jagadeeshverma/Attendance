package com.example.attendance.service;

import com.example.attendance.dto.AttendanceDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceDTO login(Long userId);
    AttendanceDTO logout(Long userId);
    List<AttendanceDTO> getAttendanceForUser(Long userId);
}
