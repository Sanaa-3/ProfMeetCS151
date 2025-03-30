package s25.cs151.application;
import java.util.Objects;

public class Course {
    private String courseCode;
    private String courseName;
    private String sectionNumber;

    // Constructor
    public Course(String courseCode, String courseName, String sectionNumber) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
    }

    // getters and setters
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getSectionNumber() { return sectionNumber; }
    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }

    // for CSV storage
    @Override
    public String toString() {
        return courseCode + "," + courseName + "," + sectionNumber;
    }

    // duplicate checking
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseCode.equals(course.courseCode) &&
                courseName.equals(course.courseName) &&
                sectionNumber.equals(course.sectionNumber);
    }
}
