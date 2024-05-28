// controller/AttendanceController.java
package com.example.attendance.controller;

import com.example.attendance.dto.AttendanceDTO;
import com.example.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/login")
    public AttendanceDTO login(@RequestParam Long userId) {
        return attendanceService.login(userId);
    }

    @PostMapping("/logout")
    public AttendanceDTO logout(@RequestParam Long userId) {
        return attendanceService.logout(userId);
    }

    @GetMapping
    public List<AttendanceDTO> getAttendanceForUser(@RequestParam Long userId) {
        return attendanceService.getAttendanceForUser(userId);
    }
}
