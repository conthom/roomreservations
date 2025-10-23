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

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testReservations() throws Exception 	{

	}
}