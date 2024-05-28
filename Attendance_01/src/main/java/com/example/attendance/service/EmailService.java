// service/EmailService.java
package com.example.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final String adminEmail = "jagadeesh14143@gmail.com"; // Replace with admin email
    private final String teamLeadEmail = "Jagadeesh.k@rjaytechnologies.com";
    
    public void sendLateLoginNotification(String userName, LocalDateTime loginTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail,teamLeadEmail);
        message.setSubject("Late Login Alert");
        message.setText("User " + userName + " logged in at " + loginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " which is after the allowed login time.");

        mailSender.send(message);
    }

    public void sendEarlyLogoutNotification(String userName, LocalDateTime loginTime, LocalDateTime logoutTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail,teamLeadEmail);
        message.setSubject("Early Logout Alert");
        message.setText("User " + userName + " logged out early at " + logoutTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " after logging in at " + loginTime 
                        + ". Total working time was less than 9 hours.");

        mailSender.send(message);
    }
}

