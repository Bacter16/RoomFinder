package org.roomfinder.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "rooms")
public class Room implements java.io.Serializable {

    @EmbeddedId
    private RoomID id;

    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "price")
    private float price;

    @Column(name = "is_available")
    private boolean isAvailable;

    @ManyToOne
    @MapsId("hotelId")
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Room() {
    }

    public Room(RoomID id, RoomType roomType, float price, boolean isAvailable, Hotel hotel) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
    }

    public RoomID getId() {
        return id;
    }

    public void setId(RoomID id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getHotel() {
        return hotel != null ? hotel.getId() : null;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", hotel=" + hotel +
                '}';
    }
}
