package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.StudentDao;
import com.example.demo.entity.Student;

@SpringBootTest
public class StudentServiceTest {

	@MockBean
	private StudentDao sd;

	@Autowired
	StudentService ss;

	@Test
	public void saveStuTest() {

		// positive scenario
		Student s = new Student(6, "kiran", "Kumar", 28);

		when(sd.save(ArgumentMatchers.any())).thenReturn(s);
		String saveStu = ss.saveStu(s);
		assertEquals("Student saved successfully", saveStu);

		// negative scenario
		when(sd.save(ArgumentMatchers.any())).thenThrow(RuntimeException.class);
		String exception = ss.saveStu(s);
		assertEquals("Student Not saved", exception);

	}

	@Test
	public void getAllStuTest() {
		when(sd.findAll()).thenReturn(new ArrayList<>());
		List<Student> allStudent = ss.getAllStu();
		assertEquals(0, allStudent.size());
	}

	@Test
	public void getStuByIdTest() {

		// positive scenario
		Student s = new Student(4, "Jagadeesh", "Varma", 20);
		when(sd.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(s));
		Student stuById = ss.getStuById(4);
		assertEquals("Varma", stuById.getLastName());

		// negative scenario
		when(sd.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> {
			ss.getStuById(999);
		});
	}

	@Test
	public void updateStuTest() {

		// positive scenario
		Student s = new Student(2, "kiran", "Kumar", 28);
		when(sd.save(ArgumentMatchers.any())).thenReturn(s);
		when(sd.findById(ArgumentMatchers.any())).thenReturn(Optional.of(s));
		String actual = ss.updateStu(s);
		assertEquals("student Data Updated Successfully", actual);

		// negative scenario
		// Student s = new Student(2, "kiran", "Kumar", 28);
		when(sd.save(ArgumentMatchers.any())).thenThrow(RuntimeException.class);
		when(sd.findById(ArgumentMatchers.any())).thenReturn(Optional.of(s));
		String actualNeg = ss.updateStu(s);
		assertEquals("student Data Not Updated", actualNeg);

	}

	@Test
	public void deleteStuTest() {

		// positive scenario
		doNothing().when(sd).deleteById(anyInt());
		String actual = ss.deleteStu(10);
		assertEquals("student Data Deleted Successfully", actual);

		// negative scenario
		when(ss.deleteStu(20)).thenThrow(RuntimeException.class);
		String deleteStu = ss.deleteStu(20);
		assertEquals("student Data Not Deleted", deleteStu);
	}
}
