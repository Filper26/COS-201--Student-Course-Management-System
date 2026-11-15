//Compile using: javac StudentCourseManagement.java//
//Run using: java StudentCourseManagement//


//Student Course Management System//

```java
import java.util.*;
import java.io.*;

class Course {
    String code, title;
    int units;

    Course(String code, String title, int units) {
        this.code = code;
        this.title = title;
        this.units = units;
    }

    void display() {
        System.out.println(code + " | " + title + " | " + units + " units");
    }
}

public class StudentCourseManagement {
    static ArrayList<Course> courses = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static final String FILE = "courses.txt";

    // Add a new course
    static void addCourse() {
        try {
            System.out.print("Enter course code: ");
            String code = input.nextLine().trim().toUpperCase();

            if (code.isEmpty() || code.contains("|"))
                throw new IllegalArgumentException("Invalid course code.");

            for (Course c : courses) {
                if (c.code.equalsIgnoreCase(code)) {
                    System.out.println("Course already exists.");
                    return;
                }
            }

            System.out.print("Enter course title: ");
            String title = input.nextLine().trim();

            if (title.isEmpty() || title.contains("|"))
                throw new IllegalArgumentException("Invalid course title.");

            System.out.print("Enter credit units: ");
            int units = Integer.parseInt(input.nextLine().trim());

            if (units <= 0)
                throw new IllegalArgumentException("Units must be positive.");

            courses.add(new Course(code, title, units));
            System.out.println("Course added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Display all courses
    static void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses recorded.");
            return;
        }

        for (Course c : courses)
            c.display();
    }

    // Search for a course
    static void searchCourse() {
        System.out.print("Enter course code: ");
        String code = input.nextLine().trim();

        for (Course c : courses) {
            if (c.code.equalsIgnoreCase(code)) {
                c.display();
                return;
            }
        }

        System.out.println("Course not found.");
    }

    // Recursive method to calculate total units
    static int totalUnits(int index) {
        if (index == courses.size())
            return 0;

        return courses.get(index).units + totalUnits(index + 1);
    }

    // Save courses to a file
    static void saveCourses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            for (Course c : courses) {
                writer.println(c.code + "|" + c.title + "|" + c.units);
            }
            System.out.println("Courses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving courses.");
        }
    }

    // Load courses from a file
    static void loadCourses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            ArrayList<Course> loaded = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length != 3)
                    throw new IOException("Invalid file format.");

                int units = Integer.parseInt(data[2]);

                if (data[0].trim().isEmpty() ||
                    data[1].trim().isEmpty() || units <= 0)
                    throw new IOException("Invalid course data.");

                loaded.add(new Course(data[0], data[1], units));
            }

            courses.clear();
            courses.addAll(loaded);
            System.out.println("Courses loaded successfully.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
    }

    // Main menu
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== STUDENT COURSE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Compute Total Units");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit");

            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1": addCourse(); break;
                case "2": viewCourses(); break;
                case "3": searchCourse(); break;
                case "4":
                    System.out.println("Total Units: " +
                        totalUnits(0));
                    break;
                case "5": saveCourses(); break;
                case "6": loadCourses(); break;
                case "7":
                    System.out.println("Program terminated.");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
```

