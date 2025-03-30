package s25.cs151.application;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlots {
    private LocalTime startTime;
    private LocalTime endTime;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

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

    public String getStartTimeString() {
        return startTime.format(FORMATTER);
    }

    public String getEndTimeString() {
        return endTime.format(FORMATTER);
    }
}
