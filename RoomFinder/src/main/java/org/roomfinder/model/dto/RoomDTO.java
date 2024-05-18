package org.roomfinder.model.dto;

public class RoomDTO {
    private int roomNumber;
    private int type;
    private float price;
    private boolean isAvailable;
    private long hotelId;

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomDTO(int roomNumber, int type, float price, boolean isAvailable, long hotelId) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.hotelId = hotelId;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "roomNumber=" + roomNumber +
                ", type=" + type +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", hotelId=" + hotelId +
                '}';
    }
}
