package s25.cs151.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {
    private static final String CSV_FILE = "src/main/java/s25/cs151/application/courses.csv";
    private List<Course> coursesList;

    public CoursesDAO() {
        coursesList = new ArrayList<>();
        loadFromCSV();
    }

    // Load data from CSV
    public void loadFromCSV() {
        coursesList.clear();
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3); // Split into code, name, section
                if (parts.length == 3) {
                    String courseCode = parts[0];
                    String courseName = parts[1];
                    String sectionNumber = parts[2];
                    Course course = new Course(courseCode, courseName, sectionNumber);
                    coursesList.add(course);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading courses CSV: " + e.getMessage());
        }
    }

    // Store data to CSV
    public void storeToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Course course : coursesList) {
                bw.write(course.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error storing courses to CSV: " + e.getMessage());
        }
    }

    // Save a new course
    public void save(Course course) {
        if (coursesList.contains(course)) {
            throw new IllegalStateException("Duplicate course: " + course.getCourseCode() + " " +
                    course.getCourseName() + " Section " + course.getSectionNumber());
        }
        coursesList.add(course);
        storeToCSV();
    }
}
