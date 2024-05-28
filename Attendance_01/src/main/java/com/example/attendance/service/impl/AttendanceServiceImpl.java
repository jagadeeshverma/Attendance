// service/impl/AttendanceServiceImpl.java
package com.example.attendance.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.attendance.dto.AttendanceDTO;
import com.example.attendance.dto.UserDTO;
import com.example.attendance.model.Attendance;
import com.example.attendance.model.User;
import com.example.attendance.repository.AttendanceRepository;
import com.example.attendance.repository.UserRepository;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.service.EmailService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;

    @Override
    public AttendanceDTO login(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        LocalDateTime now = LocalDateTime.now();
        
        // Check if the user is already logged in
        List<Attendance> ongoingAttendances = attendanceRepository.findByUserAndLogoutTimeIsNull(user.get());
        if (!ongoingAttendances.isEmpty()) {
            throw new RuntimeException("User is already logged in.");
        }  
     
        Attendance attendance = new Attendance();
        attendance.setUser(user.get());
        attendance.setLoginTime(now);
        attendanceRepository.save(attendance);
        
        // Check for late login and send notification if necessary
        LocalTime allowedLoginTime = LocalTime.of(10, 0);  // 9:00 AM
        if (now.toLocalTime().isAfter(allowedLoginTime)) {
        	emailService.sendLateLoginNotification(user.get().getName(), now);
        }

        return convertToDTO(attendance);
    }

    @Override
    public AttendanceDTO logout(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Find the user's last login record with no logout time
        List<Attendance> ongoingAttendances = attendanceRepository.findByUserAndLogoutTimeIsNull(user.get());
        if (ongoingAttendances.isEmpty()) {
            throw new RuntimeException("User is not logged in.");
        }

        Attendance attendance = ongoingAttendances.get(0);
        attendance.setLogoutTime(LocalDateTime.now());
        
        // Calculate total working hours
        Duration duration = Duration.between(attendance.getLoginTime(), attendance.getLogoutTime());
        attendance.setTotalWorkingHours(duration.toMinutes() / 60.0);

        // Check if the working duration is less than 9 hours
        if (duration.toHours() < 9) {
            emailService.sendEarlyLogoutNotification(user.get().getName(), attendance.getLoginTime(), attendance.getLogoutTime());
        }

        attendanceRepository.save(attendance);

        return convertToDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> getAttendanceForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        List<Attendance> attendances = attendanceRepository.findByUserAndLoginTimeBetween(user.get(), LocalDateTime.now().minusDays(30), LocalDateTime.now());
        return attendances.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setId(attendance.getId());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(attendance.getUser().getId());
        userDTO.setName(attendance.getUser().getName());
        
        attendanceDTO.setUser(userDTO);
        attendanceDTO.setLoginTime(attendance.getLoginTime());
        attendanceDTO.setLogoutTime(attendance.getLogoutTime());
        attendanceDTO.setTotalWorkingHours(attendance.getTotalWorkingHours());
        
        return attendanceDTO;
    }
}
