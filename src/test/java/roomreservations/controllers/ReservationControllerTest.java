package roomreservations.controllers;

import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomreservations.data.ReservationRepository;
import roomreservations.data.RoomRepository;
import roomreservations.model.Reservation;
import roomreservations.model.Room;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testReservations() throws Exception 	{
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("reservation_form"))
				.andExpect(model().attributeExists("reservation"))
				.andExpect(model().attributeExists("reservations"))
				.andExpect(model().attributeExists("rooms"));
	}
}