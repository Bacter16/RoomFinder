package org.roomfinder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class RoomID implements Serializable {

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "hotel_id")
    private long hotelId;


    public RoomID() {
    }

    public RoomID(int roomNumber, long hotelId) {
        this.roomNumber = roomNumber;
        this.hotelId = hotelId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomID)) return false;
        RoomID roomID = (RoomID) o;
        return roomNumber == roomID.roomNumber && hotelId == roomID.hotelId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
