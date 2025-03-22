package s25.cs151.application;
import java.io.*;
import java.util.*;

public class CSVHelper {

    //file path and its name
    private static final String FILE_PATH = "office_hours.csv"; // File name

    //uses buffered reader to read file line by line, splits the data based on comma and then adds it to the list
    public static List<SemesterOfficeHours> loadOfficeHours() {
        List<SemesterOfficeHours> officeHoursList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String semester = parts[0];
                int year = Integer.parseInt(parts[1]);
                List<String> days = Arrays.asList(parts[2].split(";"));
                officeHoursList.add(new SemesterOfficeHours(semester, year, days));
            }
            //error handling
        } catch (IOException e) {
            System.err.println("Error loading office hours: " + e.getMessage());
        }

        // sort the list in descending order
        officeHoursList.sort(Comparator.comparing(SemesterOfficeHours::getYear).reversed()
                .thenComparing(o -> semesterOrder(o.getSemester()), Comparator.reverseOrder()));

        return officeHoursList;
    }


    //sorting order of semester: Spring > Summer Fall, Winter
    private static int semesterOrder(String semester) {
        switch (semester) {
            case "Spring": return 1;
            case "Summer": return 2;
            case "Fall": return 3;
            case "Winter": return 4;
            default: return 5;
        }
    }




    //saves new SemesterOfficeHours to the CSV file, opens file and converts data to csv, data is then written to file
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
