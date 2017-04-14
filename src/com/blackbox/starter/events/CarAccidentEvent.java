package com.blackbox.starter.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toktar.
 */
public class CarAccidentEvent extends CarEvent implements Serializable {


    public List<String> getBreakageCodeList() {
        return breakageCodeList;
    }

    public void setBreakageCodeList(List<String> breakageCodeList) {
        this.breakageCodeList = breakageCodeList;
    }

    private List<String> breakageCodeList = new ArrayList<>();
    
    @Override
    public String toString() {
        String eventString = "CarAccidentEvent{" + super.toString();
        
        for(String breakage: breakageCodeList) {
            eventString += ", '" + breakage + "'";
        }
        
        eventString += '}';
        
        return eventString;
    }
}
