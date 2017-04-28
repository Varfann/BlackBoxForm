package com.blackbox.starter.events;

import java.io.Serializable;
import java.util.List;

/**
 * Created by toktar.
 */
public class CarAccidentEvent extends CarEvent implements Serializable {

    /*public List<String> getBreakageCodeList() {
        return breakageCodeList;
    }

    public void setBreakageCodeList(List<String> breakageCodeList) {
        this.breakageCodeList = breakageCodeList;
    }

    private List<String> breakageCodeList = new ArrayList<>();*/
    
    private List<String> checkedSensors;
    
    private List<String> possiblyDamaged;
    
    private List<String> workedAirbags;
    
    public List<String> getCheckedSensorsList() {
        return checkedSensors;
    }

    public void setCheckedSensorsList(List<String> checkedSensors) {
        this.checkedSensors = checkedSensors;
    }
    
    public List<String> getPossiblyDamagedList() {
        return possiblyDamaged;
    }

    public void setPossiblyDamagedList(List<String> possiblyDamaged) {
        this.possiblyDamaged = possiblyDamaged;
    }
    
    public List<String> getWorkedAirbagsList() {
        return workedAirbags;
    }

    public void setWorkedAirbagsList(List<String> workedAirbags) {
        this.workedAirbags = workedAirbags;
    }
    
    @Override
    public String toString() {
        String eventString = "CarAccidentEvent{" + super.toString();
        
        /*for(String breakage: breakageCodeList) {
            eventString += ", '" + breakage + "'";
        }*/      
        
        for(String sensor: checkedSensors) {
            eventString += ", '" + sensor + "'";
        }
        
        for(String breakage: possiblyDamaged) {
            eventString += ", '" + breakage + "'";
        }
        
        for(String airbag: workedAirbags) {
            eventString += ", '" + airbag + "'";
        }
        
        eventString += '}';
        
        return eventString;
    }
}
