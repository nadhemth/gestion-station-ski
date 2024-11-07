package tn.esprit.spring;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.controllers.CourseRestController;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.services.ICourseServices;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GestionStationSkiApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private ICourseServices courseServices;

	@InjectMocks
	private CourseRestController courseRestController;

	@Autowired
	private ObjectMapper objectMapper;

	private Course mockCourse;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(courseRestController).build();
		mockCourse = new Course();
		mockCourse.setNumCourse(1L);
		mockCourse.setLevel(2);
		mockCourse.setTypeCourse(TypeCourse.INDIVIDUAL);
		mockCourse.setSupport(Support.SKI);
		mockCourse.setPrice(200.0F);
		mockCourse.setTimeSlot(3);
	}

	@Test
	public void testAddCourse() throws Exception {
		when(courseServices.addCourse(any(Course.class))).thenReturn(mockCourse);

		mockMvc.perform(post("/course/add")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(mockCourse)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numCourse").value(mockCourse.getNumCourse()))
				.andExpect(jsonPath("$.level").value(mockCourse.getLevel()))
				.andExpect(jsonPath("$.typeCourse").value(mockCourse.getTypeCourse().toString()))
				.andExpect(jsonPath("$.support").value(mockCourse.getSupport().toString()))
				.andExpect(jsonPath("$.price").value(mockCourse.getPrice()))
				.andExpect(jsonPath("$.timeSlot").value(mockCourse.getTimeSlot()));
	}

	@Test
	public void testGetAllCourses() throws Exception {
		List<Course> courses = Arrays.asList(mockCourse);
		when(courseServices.retrieveAllCourses()).thenReturn(courses);

		mockMvc.perform(get("/course/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].numCourse").value(mockCourse.getNumCourse()))
				.andExpect(jsonPath("$[0].level").value(mockCourse.getLevel()))
				.andExpect(jsonPath("$[0].typeCourse").value(mockCourse.getTypeCourse().toString()))
				.andExpect(jsonPath("$[0].support").value(mockCourse.getSupport().toString()))
				.andExpect(jsonPath("$[0].price").value(mockCourse.getPrice()))
				.andExpect(jsonPath("$[0].timeSlot").value(mockCourse.getTimeSlot()));
	}

	@Test
	public void testUpdateCourse() throws Exception {
		when(courseServices.updateCourse(any(Course.class))).thenReturn(mockCourse);

		mockMvc.perform(put("/course/update")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(mockCourse)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numCourse").value(mockCourse.getNumCourse()))
				.andExpect(jsonPath("$.level").value(mockCourse.getLevel()))
				.andExpect(jsonPath("$.typeCourse").value(mockCourse.getTypeCourse().toString()))
				.andExpect(jsonPath("$.support").value(mockCourse.getSupport().toString()))
				.andExpect(jsonPath("$.price").value(mockCourse.getPrice()))
				.andExpect(jsonPath("$.timeSlot").value(mockCourse.getTimeSlot()));
	}

	@Test
	public void testGetById() throws Exception {
		when(courseServices.retrieveCourse(mockCourse.getNumCourse())).thenReturn(mockCourse);

		mockMvc.perform(get("/course/get/{id-course}", mockCourse.getNumCourse()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numCourse").value(mockCourse.getNumCourse()))
				.andExpect(jsonPath("$.level").value(mockCourse.getLevel()))
				.andExpect(jsonPath("$.typeCourse").value(mockCourse.getTypeCourse().toString()))
				.andExpect(jsonPath("$.support").value(mockCourse.getSupport().toString()))
				.andExpect(jsonPath("$.price").value(mockCourse.getPrice()))
				.andExpect(jsonPath("$.timeSlot").value(mockCourse.getTimeSlot()));
	}
}
