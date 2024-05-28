
package com.example.attendance.service.impl;

import com.example.attendance.dto.UserDTO;
import com.example.attendance.model.User;
import com.example.attendance.repository.UserRepository;
import com.example.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO addUser(String name) {
        User user = new User();
        user.setName(name);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }
}
