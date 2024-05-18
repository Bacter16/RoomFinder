package org.roomfinder.controller;

import org.roomfinder.model.Reservation;
import org.roomfinder.model.Room;
import org.roomfinder.model.RoomID;
import org.roomfinder.model.User;
import org.roomfinder.model.dto.ReservationDTO;
import org.roomfinder.repository.ReservationRepository;
import org.roomfinder.repository.RoomRepository;
import org.roomfinder.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    ReservationRepository reservationRepository;
    UserRepository userRepository;
    RoomRepository roomRepository;

    public ReservationController(ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/hotel={hotelId}")
    public List<Reservation> getAllReservations(@PathVariable Long hotelId) {

        List<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : reservationRepository.findAll()) {
            if (reservation.getRoom().getId().getHotelId() == hotelId) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    @PostMapping("/hotel={hotelId}/room={roomNumber}")
    public Reservation addReservation(@PathVariable Long hotelId, @PathVariable int roomNumber, @RequestBody ReservationDTO reservationDTO) {

        Reservation reservation = new Reservation();

        UUID uuid = UUID.fromString(reservationDTO.getUserId());
        User user = userRepository.findById(uuid).get();
        Room room = roomRepository.findById(new RoomID(roomNumber, hotelId)).get();

        reservation.setRoom(room);
        reservation.setUser(user);

        reservation.setStartDate(getTimestampFromString(reservationDTO.getStartDate()));
        reservation.setEndDate(getTimestampFromString(reservationDTO.getEndDate()));

        if(reservation.getStartDate().after(reservation.getEndDate())){
            return null;
        }

        List<Reservation> reservations = getAllReservations(reservation.getRoom().getId().getHotelId());

        for (Reservation r : reservations) {
            if (r.getRoom().getId().getRoomNumber() == reservation.getRoom().getId().getRoomNumber()) {
                if (r.getStartDate().before(reservation.getStartDate()) && r.getEndDate().after(reservation.getStartDate())) {
                    return null;
                }
                if (r.getStartDate().before(reservation.getEndDate()) && r.getEndDate().after(reservation.getEndDate())) {
                    return null;
                }
                if (r.getStartDate().after(reservation.getStartDate()) && r.getEndDate().before(reservation.getEndDate())) {
                    return null;
                }
                if (r.getStartDate().equals(reservation.getStartDate()) && r.getEndDate().equals(reservation.getEndDate())) {
                    return null;
                }
            }
        }


        return reservationRepository.save(reservation);
    }

    private static Timestamp getTimestampFromString(String date) {
        return Timestamp.valueOf(date);
    }

}
