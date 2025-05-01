package s25.cs151.application;

public class Appointment {
    private String studentName;
    private String date;
    private String timeSlot;
    private String course;
    private String reason;
    private String comment;

    public Appointment(String studentName, String date, String timeSlot, String course, String reason, String comment) {
        this.studentName = studentName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    // Getters
    public String getStudentName() { return studentName; }
    public String getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public String getCourse() { return course; }
    public String getReason() { return reason; }
    public String getComment() { return comment; }

    // Optional: Setters (if you want to edit them later)
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setDate(String date) { this.date = date; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setCourse(String course) { this.course = course; }
    public void setReason(String reason) { this.reason = reason; }
    public void setComment(String comment) { this.comment = comment; }
}
