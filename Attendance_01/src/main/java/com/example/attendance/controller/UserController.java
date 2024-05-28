
package com.example.attendance.controller;

import com.example.attendance.dto.UserDTO;
import com.example.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		return userService.addUser(userDTO.getName());
	}

	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	@GetMapping
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}
}
