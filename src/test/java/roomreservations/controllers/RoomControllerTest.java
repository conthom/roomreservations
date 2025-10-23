package roomreservations.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import roomreservations.data.ReservationRepository;
import roomreservations.data.RoomRepository;
import roomreservations.model.Reservation;
import roomreservations.model.Room;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testRooms() throws Exception 	{

	}
}
