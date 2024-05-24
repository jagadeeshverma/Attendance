package com.example.demo.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.controller.StudentController;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = StudentController.class)
public class StudentRestTest {

	@MockBean
	private StudentService ss;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllStuTest() {
		when(ss.getAllStu()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get("/allStudent");
		try {
			ResultActions perform = mockMvc.perform(req);
			MockHttpServletResponse response = perform.andReturn().getResponse();
			int status = response.getStatus();
			assertEquals(200, status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveStuTest() {
		when(ss.saveStu(ArgumentMatchers.any())).thenReturn("Success");
		Student s = new Student(7, "raghu", "kumar", 43);
		try {
			String jsonobj = new ObjectMapper().writeValueAsString(s);
			MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/save")
					.contentType(MediaType.APPLICATION_JSON).content(jsonobj);
			ResultActions perform = mockMvc.perform(req);
			MockHttpServletResponse response = perform.andReturn().getResponse();
			int status = response.getStatus();
			assertEquals(201, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void deleteStuTest() {
		int id=7;
		when(ss.deleteStu(id)).thenReturn("Deleted");
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders.delete("/delete").param("id", String.valueOf(id));
		try {
			ResultActions perform = mockMvc.perform(req);
			MockHttpServletResponse response = perform.andReturn().getResponse();
			int status = response.getStatus();
			assertEquals(301, status);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateStuTest() {
		when(ss.updateStu(ArgumentMatchers.any())).thenReturn("Updated");
		 Student student = new Student(2, "Anil", "Kumar", 25);
		 
		 try {
			 String jsonobj = new ObjectMapper().writeValueAsString(student);
				MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/updateStu")
						.contentType(MediaType.APPLICATION_JSON).content(jsonobj);
				ResultActions perform = mockMvc.perform(req);
				MockHttpServletResponse response = perform.andReturn().getResponse();
				int status = response.getStatus();
				assertEquals(405, status);
			
		} catch (Exception e) {
		e.printStackTrace();
		}
	}

}
