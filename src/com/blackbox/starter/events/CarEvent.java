package com.blackbox.starter.events;

import java.io.Serializable;

/**
 * Created by toktar.
 */
public abstract class CarEvent implements Serializable, ICarEvent {

    protected long timestamp;
    protected String eventId;
    protected long mileage;


    @Override
    public int compareTo(Object obj) {
        CarEvent carEvent = (CarEvent)obj;
        if(carEvent.getEventId().equals(this.getEventId())) return 0;
        return carEvent.getTimestamp()<this.getTimestamp()?1:-1;

    }

    @Override
    public String toString() {
        return  "timestamp=" + timestamp +
                ", eventId='" + eventId + '\'' +
                ", mileage=" + mileage;
    }

    public long getMileage() {
        return mileage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public int compare(CarEvent event1, CarEvent event2) {
        return event1.getTimestamp()>event2.getTimestamp()?1:-1;
    }
}
