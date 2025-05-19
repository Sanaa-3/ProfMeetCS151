# ProfMeet: Faculty Office Hours Manager

ProfMeet is a desktop application built using JavaFX that allows faculty members and students to manage office hour appointments. The application provides a simple and user-friendly interface for viewing, adding, editing, and deleting scheduled appointments. All data is stored locally in a CSV file to ensure persistent access across sessions.

## Features

- View all scheduled office hour appointments in a table view
- Add new appointments including:
  - Student name
  - Date
  - Time slot
  - Course
  - Reason
  - Additional comments
- Edit and delete existing appointments
- Data is saved and loaded from a CSV file
- Designed with JavaFX using a modular structure (MVC pattern)

## Technologies Used

- Java 17+
- JavaFX for the graphical user interface
- Standard Java I/O for CSV file handling
- IntelliJ IDEA (or any other compatible IDE)

## Project Structure
src/
└── s25/
└── cs151/
└── application/
├── Main.java # Entry point for the application


## How to Run

1. Ensure you have Java 17 or higher installed.
2. Clone or download this repository.
3. Open the project in your preferred IDE (e.g., IntelliJ IDEA).
4. Make sure JavaFX libraries are correctly configured.
5. Run the `Main.java` class located in `s25.cs151.application`.

## CSV File Format

The application reads and writes appointment data to a local CSV file named `appointments.csv`. Each row contains the following fields:

- Student Name
- Date
- Time Slot
- Course
- Reason
- Comment

Ensure this file exists in the appropriate directory, or the application will create one upon saving new appointments.

## Future Improvements (Optional)

- Add form validation for inputs
- Integrate calendar-based selection for dates
- Provide filtering and search functionality for appointments
- Add export or print features for records

## License

This project is for academic purposes and is not intended for production use. All rights reserved by the authors.
Sanaa Stanezai
Vaishnavi Panchal
Huy Mai 
Andrea Tapia
