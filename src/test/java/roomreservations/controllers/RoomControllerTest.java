package roomreservations.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testRoomsLoads() throws Exception 	{
		mockMvc.perform(get("/rooms"))
				.andExpect(status().isOk())
				.andExpect(view().name("rooms"))
				.andExpect(model().attributeExists("rooms"));
	}
	@Test
	void testSearchRooms_BuildingAndCapacity() throws Exception {
		LocalDate date = LocalDate.now().plusDays(1);
		LocalTime start = LocalTime.of(10, 0);
		LocalTime end = LocalTime.of(11, 0);

		mockMvc.perform(get("/rooms/search")
						.param("building", "Main Building")
						.param("capacity", "10")
						.param("date", date.toString())
						.param("startTime", start.toString())
						.param("endTime", end.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("reservation_form"))
				// Should be two rooms in Main Building with at least 10 capacity
				.andExpect(model().attribute("rooms", hasSize(2)))
				.andExpect(model().attributeExists("reservation"))
				.andExpect(model().attributeExists("reservations"));
	}
	@Test
	void testSearchRooms_ByProjectorAndWhiteboard() throws Exception {
		LocalDate date = LocalDate.now().plusDays(1);
		LocalTime start = LocalTime.of(9, 0);
		LocalTime end = LocalTime.of(10, 0);

		mockMvc.perform(get("/rooms/search")
						.param("hasProjector", "true")
						.param("hasWhiteboard", "true")
						.param("date", date.toString())
						.param("startTime", start.toString())
						.param("endTime", end.toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("reservation_form"))
				// Should be 5 rooms with both projectors and whiteboards
				.andExpect(model().attribute("rooms", hasSize(5)))
				.andExpect(model().attributeExists("reservation"))
				.andExpect(model().attributeExists("reservations"));
	}
}
