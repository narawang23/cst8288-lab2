package org.cst8288Lab2.util;

public class Validator {

    public static void validateStudent(String id, String firstName, String lastName) throws Exception {
        if (id.length() != 9 || !id.matches("\\d{9}")) {
            throw new Exception("Invalid student ID: " + id);
        }
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new Exception("Invalid student name: " + firstName + " " + lastName);
        }
    }

    public static void validateCourse(String id, String name) throws Exception {
        if (!id.matches("[a-zA-Z]{3}\\d{4}")) {
            throw new Exception("Invalid course ID: " + id);
        }
        if (name.isEmpty()) {
            throw new Exception("Invalid course name: " + name);
        }
    }

    public static int validateTerm(String term) throws Exception {
        switch (term.toUpperCase()) {
            case "WINTER":
                return 1;
            case "SUMMER":
                return 2;
            case "FALL":
                return 3;
            default:
                throw new Exception("Invalid term: " + term);
        }
    }

    public static int validateYear(String year) throws Exception {
        if (year.length() != 4 || !year.matches("\\d{4}")) {
            throw new Exception("Invalid year format: " + year);
        }
        int yearInt = Integer.parseInt(year);
        if (yearInt < 1967 || yearInt > java.time.Year.now().getValue()) {
            throw new Exception("Invalid year: " + year);
        }
        return yearInt;
    }
}
