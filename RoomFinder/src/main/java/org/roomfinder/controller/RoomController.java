package org.roomfinder.controller;

import org.roomfinder.model.Room;
import org.roomfinder.repository.RoomRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/hotel={hotelId}")
    public List<Room> rooms(@PathVariable("hotelId") int hotelId) {

        List<Room> rooms = new ArrayList<>();

        for (Room room : roomRepository.findAll()) {
            if (room.getId().getHotelId() == hotelId) {
                rooms.add(room);
            }
        }

        return rooms;

    }

    @GetMapping("/hotel={hotelId}/room={roomNumber}")
    public Room room(@PathVariable("hotelId") int hotelId, @PathVariable("roomNumber") int roomNumber) {

        for (Room room : roomRepository.findAll()) {
            if (room.getId().getHotelId() == hotelId && room.getId().getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;

    }

}
