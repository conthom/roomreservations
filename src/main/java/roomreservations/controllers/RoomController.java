package roomreservations.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	private final RoomRepository roomRepository;
	private final ReservationRepository reservationRepository;

	public RoomController(RoomRepository roomRepository, ReservationRepository reservationRepository) {
		this.roomRepository = roomRepository;
		this.reservationRepository = reservationRepository;
	}

	@GetMapping
	public String listRooms(Model model) {
		model.addAttribute("rooms", roomRepository.findAll());
		return "rooms";
	}
	@GetMapping("/search")
	public String searchRooms(
			@RequestParam(required = false) String building,
			@RequestParam(required = false) Integer capacity,
			@RequestParam(required = false) Boolean hasProjector,
			@RequestParam(required = false) Boolean hasWhiteboard,
			@RequestParam(required = false) Boolean hasWindows,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
			Model model
	) {
		// Requires date and time input for a reservation
		if (date == null || startTime == null || endTime == null) {
			model.addAttribute("error", "Date, start time, and end time are required to search for available rooms.");
			model.addAttribute("rooms", List.of());
			model.addAttribute("reservation", new Reservation());
			return "reservation_form";
		}
		// Treat empty strings as null
		if (building != null && building.isEmpty()){
			building = null;
		}

		// Query database using RoomRepository function if there's search parameters
		List<Room>  rooms = roomRepository.findRooms(building, capacity, hasProjector, hasWhiteboard, hasWindows);

		// Filter out rooms that aren't available in the search date and time
		rooms = rooms.stream()
				.filter(room -> reservationRepository.findConflictingReservations(room.getId(), date, startTime, endTime).isEmpty())
				.toList();

		// Add rooms to model and initialize reservations
		model.addAttribute("rooms", rooms);
		model.addAttribute("reservation", new Reservation());
		model.addAttribute("reservations", reservationRepository.findAll());
		// Preserve search fields
		model.addAttribute("building", building);
		model.addAttribute("capacity", capacity);
		model.addAttribute("hasProjector", hasProjector);
		model.addAttribute("hasWhiteboard", hasWhiteboard);
		model.addAttribute("hasWindows", hasWindows);
		model.addAttribute("date", date);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		return "reservation_form";
	}
}
