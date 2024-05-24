package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.StudentDao;
import com.example.demo.entity.Student;

@Service
public class StudentService {

	@Autowired
	private StudentDao sd;

	public String saveStu(Student student) {
		try {
			Student save = sd.save(student);
			return "Student saved successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Student Not saved";
		}

	}

	public List<Student> getAllStu() {

		List<Student> alldata = sd.findAll();
		return alldata;
	}

	public String updateStu(Student student) {
		try {
			Student save = sd.save(student);
			return "student Data Updated Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "student Data Not Updated";
		}
	}

	public Student getStuById(int id) {
		Optional<Student> byId = sd.findById(id);
		Student s = null;
		if (byId.isPresent()) {
			s = byId.get();
		} else {
			throw new RuntimeException("Student not found with id : ");
		}
		return s;
	}

	public String deleteStu(int id) {

		try
		{
			sd.deleteById(id);
			return "student Data Deleted Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "student Data Not Deleted";
		} 

	}

}
