package s25.cs151.application;

import java.time.LocalTime;

public class TimeSlots {
    private LocalTime startTime;
    private LocalTime endTime;
    public TimeSlots(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
}
