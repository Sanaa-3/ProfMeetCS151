package s25.cs151.application;
import java.io.*;
import java.util.*;

public class CSVHelper {

    // file path and its name
    private static final String FILE_PATH = "office_hours.csv"; // File name

    // loads office hours from the CSV file
    public static List<SemesterOfficeHours> loadOfficeHours() {
        List<SemesterOfficeHours> officeHoursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                // Split line by comma
                String[] parts = line.split(",");

                // Skip lines that don't have enough data
                if (parts.length < 3) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                // Extract semester, year, and days
                String semester = parts[0].trim();
                int year;
                try {
                    year = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid year format in line: " + line);
                    continue;
                }

                // Normalize and split days by semicolon and trim extra spaces
                List<String> days = Arrays.asList(parts[2].split(";"));
                days = normalizeDays(days);  // Normalize the days

                // Add the parsed data to the list
                officeHoursList.add(new SemesterOfficeHours(semester, year, days));
                System.out.println("Loaded: " + semester + ", " + year + ", " + days); // Debugging log
            }
        } catch (IOException e) {
            System.err.println("Error loading office hours: " + e.getMessage());
        }

        // Sort the list in descending order by year and semester
        officeHoursList.sort(Comparator.comparing(SemesterOfficeHours::getYear).reversed()
                .thenComparing(o -> semesterOrder(o.getSemester()), Comparator.reverseOrder()));

        System.out.println("Loaded " + officeHoursList.size() + " office hours records."); // Debugging log
        return officeHoursList;
    }

    // Normalize the days: capitalize the first letter
    private static List<String> normalizeDays(List<String> days) {
        List<String> normalizedDays = new ArrayList<>();
        for (String day : days) {
            String normalizedDay = day.trim().substring(0, 1).toUpperCase() + day.trim().substring(1).toLowerCase();
            normalizedDays.add(normalizedDay);
        }
        return normalizedDays;
    }

    // Sorting order of semester: Spring > Summer > Fall > Winter
    private static int semesterOrder(String semester) {
        switch (semester) {
            case "Spring": return 1;
            case "Summer": return 2;
            case "Fall": return 3;
            case "Winter": return 4;
            default: return 5;
        }
    }

    // Saves new SemesterOfficeHours to the CSV file
    public static void saveOfficeHours(SemesterOfficeHours newOfficeHours) {
        System.out.println("Saving office hours: " + newOfficeHours);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) { // Append mode
            String line = newOfficeHours.getSemester() + "," + newOfficeHours.getYear() + "," + String.join(";", newOfficeHours.getDays());
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving office hours: " + e.getMessage());
        }
    }
}