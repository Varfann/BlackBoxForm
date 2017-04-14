package com.blackbox.starter.events;

import java.io.Serializable;

/**
 * Created by toktar.
 */
public class CarStartEvent extends CarEvent implements Serializable {

    private String driverId;
    private String tripId;

    @Override
    public String toString() {
        return "CarStartEvent{" + super.toString() +
                ", driverId='" + driverId + '\'' +
                ", tripId='" + tripId + '\'' +
                '}';
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
