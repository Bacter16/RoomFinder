package org.roomfinder.model;

import jakarta.persistence.*;

@Entity
public class Feedback implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
