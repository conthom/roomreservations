package roomreservations.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import roomreservations.AuthService;
import roomreservations.data.ReservationRepository;
import roomreservations.data.RoomRepository;
import roomreservations.model.Reservation;
import roomreservations.model.Room;
import org.springframework.validation.BindingResult;
import roomreservations.model.Users;
import java.util.List;

@Controller
@RequestMapping("/")
public class ReservationController {

	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final AuthService authService;


	public ReservationController(ReservationRepository reservationRepository, RoomRepository roomRepository, AuthService authService) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
		this.authService = authService;
	}

	public Reservation reservationModel() {
		return new Reservation();
	}

	@GetMapping
	public String showForm(Model model, HttpServletRequest request) {
		Reservation reservation = new Reservation();
		Users loggedInUser = authService.getLoggedInUser();
		if (loggedInUser != null) {
			reservation.setReservedBy(loggedInUser.getFirstName());
		}
		model.addAttribute("reservation", reservation);
		model.addAttribute("reservations", reservationRepository.findAll());
		model.addAttribute("loggedInAs", loggedInUser);
		// rooms list
		model.addAttribute("rooms", List.of());
		return "reservation_form";
	}

	@PostMapping
	public String submitReservation(
			@ModelAttribute Reservation reservation,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		// Get the logged-in user and pre-fill reservedBy
		Users loggedInUser = authService.getLoggedInUser();
		if (loggedInUser != null) {
			reservation.setReservedBy(loggedInUser.getFirstName());
		}
		if (loggedInUser == null) {
			// Show error message
			model.addAttribute("error", "You must be signed in to make a reservation.");
			model.addAttribute("reservation", reservation);
			model.addAttribute("rooms", roomRepository.findAll());
			model.addAttribute("loggedInAs", null);
			return "reservation_form";
		}
		// Validate the reservation
		Room room = reservation.getRoom();
		if (room == null || room.getId() == null) {
			bindingResult.rejectValue("room", "reservation.room.required", "Please select a room");
		} else {
			roomRepository.findById(room.getId()).ifPresent(reservation::setRoom);
		}
		if (reservation.getStartTime() != null && reservation.getEndTime() != null &&
				!reservation.getEndTime().isAfter(reservation.getStartTime())) {
			bindingResult.rejectValue("endTime", "reservation.endTime.invalid", "End time must be after start time");
		}
		// Check for conflicts if no errors so far
		if (!bindingResult.hasErrors() && reservation.getRoom() != null) {
			List<Reservation> conflicts = reservationRepository.findConflictingReservations(
					reservation.getRoom().getId(),
					reservation.getDate(),
					reservation.getStartTime(),
					reservation.getEndTime()
			);
			if (!conflicts.isEmpty()) {
				bindingResult.reject("reservation.conflict", "The selected room is already reserved for that time");
			}
		}
		// If there are errors, reload the form
		if (bindingResult.hasErrors()) {
			model.addAttribute("reservations", reservationRepository.findAll());
			model.addAttribute("rooms", roomRepository.findAll());
			model.addAttribute("loggedInAs", loggedInUser);
			return "reservation_form";
		}
		// Save the reservation
		reservationRepository.save(reservation);
		// Add flash attributes for success message
		redirectAttributes.addFlashAttribute("successMessage", "Reservation successfully made");
		redirectAttributes.addFlashAttribute("reservationDetails", reservation);
		return "redirect:/";
	}
}
