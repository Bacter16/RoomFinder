package org.roomfinder.controller;

import org.roomfinder.model.Hotel;
import org.roomfinder.model.Room;
import org.roomfinder.model.dto.DTOUtils;
import org.roomfinder.repository.FeedbackRepository;
import org.roomfinder.repository.HotelRepository;
import org.roomfinder.repository.RoomRepository;
import org.roomfinder.util.FileReader;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("")
public class MainController {

    HotelRepository hotelRepository;
    RoomRepository roomRepository;
    FeedbackRepository feedbackRepository;

    FileReader reader;

    public MainController(HotelRepository hotelRepository, RoomRepository roomRepository, FeedbackRepository feedbackRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.feedbackRepository = feedbackRepository;
        reader = new FileReader("src/main/resources/hotels.json");
    }

    @GetMapping("/fill_DB_from_JSON_file")
    public void fillDB() {
        roomRepository.deleteAll();
        feedbackRepository.deleteAll();
        hotelRepository.deleteAll();
        Pair<List<Hotel>,List<Room>> hotelsAndRooms = DTOUtils.getHotelsFromDTO(reader.ReadFromFile());
        hotelRepository.saveAll(hotelsAndRooms.getFirst());
        roomRepository.saveAll(hotelsAndRooms.getSecond());
    }

}
