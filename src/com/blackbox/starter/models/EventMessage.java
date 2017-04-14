package com.blackbox.starter.models;


import com.blackbox.starter.events.*;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * Created by toktar.
 */

public class EventMessage {
    private CarStartEvent start;
    private CarStopEvent stop;
    private CarRepairEvent repair;
    private CarAccidentEvent accident;
    private CarBreakingEvent breaking;

    public CarStartEvent getStart() {
        return start;
    }

    public void setStart(CarStartEvent start) {
        this.start = start;
    }

    public CarStopEvent getStop() {
        return stop;
    }

    public void setStop(CarStopEvent stop) {
        this.stop = stop;
    }

    public CarRepairEvent getRepair() {
        return repair;
    }

    public void setRepair(CarRepairEvent repair) {
        this.repair = repair;
    }

    public CarAccidentEvent getAccident() {
        return accident;
    }

    public void setAccident(CarAccidentEvent accident) {
        this.accident = accident;
    }

    public CarBreakingEvent getBreaking() {
        return breaking;
    }

    public void setBreaking(CarBreakingEvent breaking) {
        this.breaking = breaking;
    }
}
