package org.roomfinder.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Reservation implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "room_number", referencedColumnName = "room_number"),
            @JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
    })
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    public Reservation() {
    }

    public Reservation(Room room, User user, Timestamp startDate, Timestamp endDate) {
        this.room = room;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", room=" + room +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
