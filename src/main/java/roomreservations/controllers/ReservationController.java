package roomreservations.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomreservations.data.ReservationRepository;
import roomreservations.data.RoomRepository;
import roomreservations.model.Reservation;
import roomreservations.model.Room;
import org.springframework.validation.BindingResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reserve")
public class ReservationController {

	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;

	public ReservationController(ReservationRepository reservationRepository, RoomRepository roomRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
	}

	public Reservation reservationModel() {
		return new Reservation();
	}

	@GetMapping
	public String showForm(Model model) {
		model.addAttribute("reservation", new Reservation());
		model.addAttribute("reservations", reservationRepository.findAll());
		// Show no rooms at first
		model.addAttribute("rooms", List.of());
		return "reservation_form";
	}

	@PostMapping
	public String submitReservation(@Valid @ModelAttribute Reservation reservation, BindingResult bindingResult, Model model) {
		// Find the room in the repository
		Room room = reservation.getRoom();
		if (room == null || room.getId() == null) {
			bindingResult.rejectValue("room", "reservation.room.required", "Please select a room");
		} else {
			roomRepository.findById(room.getId()).ifPresent(reservation::setRoom);
		}

		// Validate times
		if (reservation.getStartTime() != null && reservation.getEndTime() != null &&
				!reservation.getEndTime().isAfter(reservation.getStartTime())) {
			bindingResult.rejectValue("endTime", "reservation.endTime.invalid", "End time must be after start time");
		}

		// Check for conflicts if no previous errors
		if (!bindingResult.hasErrors() && reservation.getRoom() != null) {
			List<Reservation> conflicts = reservationRepository.findConflictingReservations(
					reservation.getRoom().getId(), reservation.getDate(), reservation.getStartTime(), reservation.getEndTime());
			if (!conflicts.isEmpty()) {
				bindingResult.reject("reservation.conflict", "The selected room is already reserved for that time");
			}
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("reservations", reservationRepository.findAll());
			model.addAttribute("rooms", roomRepository.findAll());
			return "reservation_form";
		}

		reservationRepository.save(reservation);
		return "redirect:/reserve";
	}
}
