package com.blackbox.starter.events;

import java.io.Serializable;

/**
 * Created by toktar.
 */
public class CarStartEvent extends CarEvent implements Serializable {

    private String driverId;
    private String tripId;
    private long mileage;

    @Override
    public String toString() {
        return "CarStartEvent{" + super.toString() +
                ", driverId='" + driverId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", mileage=" + mileage +
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
    
    public long getMileage() {
        return mileage;
    }
    
    public void setMileage(long mileage) {
        this.mileage = mileage;
    }
}