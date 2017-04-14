package com.blackbox.starter.events;

import java.io.Serializable;
import java.util.List;

/**
 * Created by toktar.
 */
public class CarStopEvent extends  CarEvent implements Serializable {

    private List<String> breakageCodeList;
    private String tripId;
    private long path;

    public long getPath() {
        return path;
    }

    public void setPath(long path) {
        this.path = path;
    }

    public List<String> getBreakageCodeList() {
        return breakageCodeList;
    }

    public void setBreakageCodeList(List<String> breakageCodeList) {
        this.breakageCodeList = breakageCodeList;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
    
    @Override
    public String toString() {
        String eventString = "CarStopEvent{" + super.toString() +
                " path='" + path + '\'' +
                ", tripId='" + tripId + '\'';
        
        for(String breakage: breakageCodeList) {
            eventString += ", '" + breakage + "'";
        }
        
        eventString += '}';
        
        return eventString;
    }
}
