package org.roomfinder.model.dto;

import org.roomfinder.model.Hotel;
import org.roomfinder.model.Room;
import org.roomfinder.model.RoomID;
import org.roomfinder.model.RoomType;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DTOUtils {

    public static Pair<List<Hotel>,List<Room>> getHotelsFromDTO(List<HotelDTO> hotelDTOList) {
        List<Hotel> hotels = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        for (HotelDTO hotelDTO : hotelDTOList) {
            Hotel hotel = new Hotel(hotelDTO.getId(), hotelDTO.getName(), hotelDTO.getLatitude(), hotelDTO.getLongitude());
            hotels.add(hotel);
            for(RoomDTO roomDTO : hotelDTO.getRooms()){
                RoomID roomID = new RoomID(roomDTO.getRoomNumber(), hotelDTO.getId());
                rooms.add(new Room(roomID, getRoomTypeFromDTO(roomDTO.getType()), roomDTO.getPrice(), roomDTO.isAvailable(), hotel));
            }
        }

        return Pair.of(hotels, rooms);
    }

    public static RoomType getRoomTypeFromDTO(int type){
        return switch (type) {
            case 0 -> RoomType.SINGLE;
            case 1 -> RoomType.DOUBLE;
            case 2 -> RoomType.SUITE;
            case 3 -> RoomType.MATRIMONIAL;
            default -> null;
        };
    }

    public static Hotel getHotelFromDTO(HotelDTO hotelDTO){
        return new Hotel(hotelDTO.getId(), hotelDTO.getName(), hotelDTO.getLatitude(), hotelDTO.getLongitude());
    }

    public static RoomDTO getDTOFromRoom(Room room){
        return new RoomDTO(room.getId().getRoomNumber(), room.getRoomType().ordinal(), room.getPrice(), room.isAvailable(), room.getId().getHotelId());
    }

}
