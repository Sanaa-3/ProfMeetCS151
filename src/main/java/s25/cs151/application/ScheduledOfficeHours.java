package s25.cs151.application;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class ScheduledOfficeHours {
    private final StringProperty studentName;
    private final StringProperty date;
    private final StringProperty timeSlot;
    private final StringProperty course;
    private final StringProperty reason;
    private final StringProperty comment;

    public ScheduledOfficeHours(String studentName, String date, String timeSlot, String course, String reason, String comment) {
        this.studentName = new SimpleStringProperty(studentName);
        this.date = new SimpleStringProperty(date);
        this.timeSlot = new SimpleStringProperty(timeSlot);
        this.course = new SimpleStringProperty(course);
        this.reason = new SimpleStringProperty(reason);
        this.comment = new SimpleStringProperty(comment);
    }

    public void setStudentName(String studentName) {
        this.studentNameProperty().set(studentName);
    }

    public void setDate(String date) {
        this.dateProperty().set(date);
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlotProperty().set(timeSlot);
    }

    public void setCourse(String course) {
        this.courseProperty().set(course);
    }

    public void setReason(String reason) {
        this.reasonProperty().set(reason);
    }

    public void setComment(String comment) {
        this.commentProperty().set(comment);
    }


    // Getter methods that return the property (for TableView binding)
    public StringProperty studentNameProperty() {
        return studentName;
    }

    public StringProperty dateProperty() {
        return date;
    }

    public StringProperty timeSlotProperty() {
        return timeSlot;
    }

    public StringProperty courseProperty() {
        return course;
    }

    public StringProperty reasonProperty() {
        return reason;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    // Getter methods that return the values directly (if needed for comparisons)
    public String getStudentName() {
        return studentName.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getTimeSlot() {
        return timeSlot.get();
    }

    public String getCourse() {
        return course.get();
    }

    public String getReason() {
        return reason.get();
    }

    public String getComment() {
        return comment.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledOfficeHours that = (ScheduledOfficeHours) o;
        return Objects.equals(getStudentName(), that.getStudentName()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTimeSlot(), that.getTimeSlot()) &&
                Objects.equals(getCourse(), that.getCourse()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getComment(), that.getComment());
    }
}
