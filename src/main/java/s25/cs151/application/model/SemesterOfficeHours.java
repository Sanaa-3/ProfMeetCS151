package s25.cs151.application.model;
import java.util.ArrayList;
import java.util.List;
public class SemesterOfficeHours {
    private String semester;
    private int year;
    private List<String> days;

    public SemesterOfficeHours(String semester, int year, List<String> days) {
        this.semester = semester;
        this.year = year;
        this.days = new ArrayList<>(days);
    }

    // getters
    public String getSemester() { return semester; }
    public int getYear() { return year; }
    public List<String> getDays() {return new ArrayList<>(days); }


}
