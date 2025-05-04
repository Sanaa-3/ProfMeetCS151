# ProfMeet:
# Version: 0.7

# who did what:
1. Vaishnavi Panchal - worked on edit appointments feature, organized mvc structure
2. Huy Mai - worked on polymorphism
3. Sanaa Stanezai - worked on edit appointments feature, helped organize data through quickStatsUI
3. Andrea Tapia - Formatting data for Edit appointment table, edit table formatting

Polymorphism
- Parent Interface Entity with toCSVString(); method in s25.cs151.application.model.Entity
- Child classes Course (in s25.cs151.application.model.Course) and TimeSlots (in s25.cs151.application.model.TimeSlots)
implements Entity and override toCSVString(); method
- CSVHelper has method saveCourse() and saveTimeSlots() that call their overwritten toCSVString() method

# Any other instruction that users need to know:



