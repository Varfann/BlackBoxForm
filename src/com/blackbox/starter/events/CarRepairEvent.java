package com.blackbox.starter.events;

import java.io.Serializable;

/**
 * Created by toktar.
 */
public class CarRepairEvent extends CarEvent implements Serializable {

    private String description;
    private String spareId;
    private WorkType workType;
    private long workshopStationId;

    public long getWorkshopStationId() {
        return workshopStationId;
    }

    public void setWorkshopStationId(long workshopStationId) {
        this.workshopStationId = workshopStationId;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String eventString = "CarRepairEvent{" + super.toString() +
                " description='" + description + '\'' +
                ", spareId='" + spareId + '\''+
                ", workType='" + workType + '\''+
                ", workshopStationId='" + workshopStationId + '\'' + '}';

        return eventString;
    }
}
