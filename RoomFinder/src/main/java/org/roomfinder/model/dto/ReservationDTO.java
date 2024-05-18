package org.roomfinder.model.dto;

import org.roomfinder.model.Room;
import org.roomfinder.model.User;

import java.sql.Timestamp;

public class ReservationDTO {

    private String userId;

    private String startDate;

    private String endDate;

    public ReservationDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
