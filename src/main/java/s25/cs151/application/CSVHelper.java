package s25.cs151.application;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CSVHelper {

    // file path and its name - changed the path
    private static final String FILE_PATH = "src/main/java/s25/cs151/application/DataFiles/office_hours.csv"; // File name
    private static final String COURSES_FILE_PATH = "src/main/java/s25/cs151/application/DataFiles/courses.csv";

    private static final String TIME_SLOTS_FILE_PATH = "src/main/java/s25/cs151/application/DataFiles/time_slots.csv";

    private static final String OFFICE_HOUR_SCHEDULE_PATH = "src/main/java/s25/cs151/application/DataFiles/scheduled_office_hours.csv";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.US);

    // loads office hours from the CSV file
    public static List<SemesterOfficeHours> loadOfficeHours() {
        List<SemesterOfficeHours> officeHoursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // use commas to split the lines
                String[] parts = line.split(",");


                if (parts.length < 3) {
                    //System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                // get the sem, year, and day
                String semester = parts[0].trim();
                int year;
                try {
                    year = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid year format in line: " + line);
                    continue;
                }

                // use semicolon to split data
                List<String> days = Arrays.asList(parts[2].split(";"));
                days = normalizeDays(days);  // Normalize the days

                // add data to list
                officeHoursList.add(new SemesterOfficeHours(semester, year, days));
                //System.out.println("Loaded: " + semester + ", " + year + ", " + days);
            }
        } catch (IOException e) {
            System.err.println("Error loading office hours: " + e.getMessage());
        }

        // sort the list in descending order by year and semester
        officeHoursList.sort(Comparator.comparing(SemesterOfficeHours::getYear).reversed()
                .thenComparing(o -> semesterOrder(o.getSemester()), Comparator.reverseOrder()));

        //System.out.println("Loaded " + officeHoursList.size() + " office hours records.");
        return officeHoursList;
    }

    // capitalize the first letter of day
    private static List<String> normalizeDays(List<String> days) {
        List<String> normalizedDays = new ArrayList<>();
        for (String day : days) {
            String normalizedDay = day.trim().substring(0, 1).toUpperCase() + day.trim().substring(1).toLowerCase();
            normalizedDays.add(normalizedDay);
        }
        return normalizedDays;
    }

    // Spring > Summer > Fall > Winter
    private static int semesterOrder(String semester) {
        switch (semester) {
            case "Spring": return 1;
            case "Summer": return 2;
            case "Fall": return 3;
            case "Winter": return 4;
            default: return 5;
        }
    }

    // save SemesterOfficeHours
    public static void saveOfficeHours(SemesterOfficeHours newOfficeHours) {
        //System.out.println("Saving office hours: " + newOfficeHours);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) { // Append mode
            String line = newOfficeHours.getSemester() + "," + newOfficeHours.getYear() + "," + String.join(";", newOfficeHours.getDays());
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving office hours: " + e.getMessage());
        }
    }

    //loads the time slots using the am pm format adn buffered reader
    public static List<TimeSlots> loadTimeSlots() {
        List<TimeSlots> timeSlotsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TIME_SLOTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 2 || values[0].trim().isEmpty() || values[1].trim().isEmpty()) {
                    //System.out.println("Skipping invalid or empty line: " + line);
                    continue; // Skip this line
                }

                try {
                    LocalTime fromHour = LocalTime.parse(values[0].trim(), FORMATTER);
                    LocalTime toHour = LocalTime.parse(values[1].trim(), FORMATTER);
                    timeSlotsList.add(new TimeSlots(fromHour, toHour));
                } catch (DateTimeParseException e) {
                    System.out.println("Skipping malformed time slot: " + line + " — " + e.getMessage());
                }
            }
            timeSlotsList.sort(Comparator.comparing(TimeSlots::getStartTime).thenComparing(TimeSlots::getEndTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeSlotsList;
    }


    public static void saveTimeSlots(List<TimeSlots> timeSlotsList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TIME_SLOTS_FILE_PATH, true))) {
            for (TimeSlots timeSlot : timeSlotsList) {
                String fromTime = timeSlot.getStartTime().format(FORMATTER);
                String toTime = timeSlot.getEndTime().format(FORMATTER);
                String line = fromTime + ", " + toTime;

                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.newLine(); // Ensures consistent line breaks
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving time slots: " + e.getMessage());
        }
    }


    public static List<Course> loadCourses() {
        List<Course> coursesList = new ArrayList<>();
        File file = new File(COURSES_FILE_PATH);
        if (!file.exists()) {
            return coursesList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",", 3); // Split into courseCode, courseName, sectionNumber
                if (parts.length < 3) {
                    System.err.println("Skipping invalid line in courses.csv: " + line);
                    continue;
                }

                String courseCode = parts[0].trim();
                String courseName = parts[1].trim();
                String sectionNumber = parts[2].trim();

                Course course = new Course(courseCode, courseName, sectionNumber);
                coursesList.add(course);
            }
        } catch (IOException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }

        return coursesList;
    }

    // Save a course to the CSV file
    public static void saveCourse(Course course) {
        List<Course> existingCourses = loadCourses();
        if (existingCourses.contains(course)) {
            throw new IllegalStateException("Duplicate course: " + course.getCourseCode() + " " +
                    course.getCourseName() + " Section " + course.getSectionNumber());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSES_FILE_PATH, true))) {
            String line = course.getCourseCode() + "," + course.getCourseName() + "," + course.getSectionNumber();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving course: " + e.getMessage());
        }
    }

    public static void saveScheduledOfficeHour(String studentName, LocalDate date, String timeSlot, String course, String reason, String comment) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OFFICE_HOUR_SCHEDULE_PATH, true))) {
            String line = String.join(",",
                    "\"" + studentName + "\"",
                    date.toString(),
                    "\"" + timeSlot + "\"",
                    "\"" + course + "\"",
                    "\"" + reason + "\"",
                    "\"" + comment + "\""
            );
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving scheduled office hour: " + e.getMessage());
        }
    }

    public static List<ScheduledOfficeHours> loadScheduledOfficeHours() {
        List<ScheduledOfficeHours> appointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(OFFICE_HOUR_SCHEDULE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Use a more reliable approach to split by commas, handling quotes around values
                String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split on commas that aren't inside quotes

                // Clean up parts by trimming and removing quotes
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].replace("\"", "").trim(); // Removing extra quotes and trimming spaces
                }

                // Ensure there are exactly 6 fields to avoid errors
                if (parts.length == 6) {
                    String studentName = parts[0];  // Student Name
                    String date = parts[1];         // Date
                    String timeSlot = parts[2];     // Time Slot
                    String course = parts[3];       // Course
                    String reason = parts[4];       // Reason
                    String comment = parts[5];      // Comment

                    // Create a new ScheduledOfficeHours object and add it to the list
                    ScheduledOfficeHours appointment = new ScheduledOfficeHours(studentName, date, timeSlot, course, reason, comment);
                    appointments.add(appointment);
                } else {
                    System.err.println("Skipping invalid line (incorrect number of columns): " + line);
                }
            }

            // Sort by date then time slot
            appointments.sort(Comparator.comparing(ScheduledOfficeHours::getDate)
                    .thenComparing(ScheduledOfficeHours::getTimeSlot));

        } catch (IOException e) {
            System.err.println("Error loading scheduled office hours: " + e.getMessage());
        }

        return appointments;
    }

    public static void deleteScheduledOfficeHours(ScheduledOfficeHours appointmentToDelete) throws IOException {
        List<ScheduledOfficeHours> allAppointments = loadScheduledOfficeHours();

        // Remove the matching appointment
        allAppointments.removeIf(appointment -> appointment.equals(appointmentToDelete));

        // Rewrite the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OFFICE_HOUR_SCHEDULE_PATH))) {
            for (ScheduledOfficeHours appointment : allAppointments) {
                String line = String.join(",",
                        "\"" + appointment.getStudentName() + "\"",
                        appointment.getDate(),
                        "\"" + appointment.getTimeSlot() + "\"",
                        "\"" + appointment.getCourse() + "\"",
                        "\"" + appointment.getReason() + "\"",
                        "\"" + appointment.getComment() + "\""
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
}