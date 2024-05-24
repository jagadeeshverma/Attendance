package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService ss;

	@PostMapping("save")
	public ResponseEntity<String> saveStu(@RequestBody Student student) {
		String created = ss.saveStu(student);
		return new ResponseEntity<>(created, HttpStatus.CREATED);

	}

	@GetMapping("allStudent")
	public ResponseEntity<List<Student>> getAllStu() {
		List<Student> allStu = ss.getAllStu();
		return new ResponseEntity<>(allStu, HttpStatus.OK);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Student> getStuById(@PathVariable int id) {
		Student std = ss.getStuById(id);
		return new ResponseEntity<>(std, HttpStatus.OK);
	}

	@PutMapping("updateStu")
	public ResponseEntity<Map<String, String>> updateStu(@RequestBody Student student) {
		String saveStu = ss.updateStu(student);
		return new ResponseEntity<>(Map.of("Status", saveStu), HttpStatus.CREATED);

	}

	@DeleteMapping("delete")
	public ResponseEntity<Map<String, String>> deleteStu(@RequestParam int id) {
		String deleted = ss.deleteStu(id);
		return new ResponseEntity<>(Map.of("status", deleted), HttpStatus.MOVED_PERMANENTLY);
	}

}
