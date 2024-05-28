package com.example.attendance.service;

import com.example.attendance.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO addUser(String name);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
}

