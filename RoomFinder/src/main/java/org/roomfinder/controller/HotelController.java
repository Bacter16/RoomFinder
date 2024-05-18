package org.roomfinder.controller;

import org.roomfinder.model.Hotel;
import org.roomfinder.model.dto.UserDTO;
import org.roomfinder.repository.HotelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/hotels")
public class HotelController {

    HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/m={m},latitude={latitude},longitude={longitude}")
    public List<Hotel> hotels(@PathVariable("m") float m, @PathVariable("latitude") float latitude, @PathVariable("longitude") float longitude) {

        List<Hotel> hotels = new ArrayList<>();

        for (Hotel hotel : hotelRepository.findAll()) {
            if (haversineDistance(latitude, longitude, hotel.getLatitude(), hotel.getLongitude()) <= m/1000) {
                hotels.add(hotel);
            }
        }

        return hotels;
    }

    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;

        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaPhi / 2.0) * Math.sin(deltaPhi / 2.0) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(deltaLambda / 2.0) * Math.sin(deltaLambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

}
