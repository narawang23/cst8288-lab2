
//public class App {
//    /**
//     * Parses the file: bulk-import.csv
//     * Validates each item in each row and updates the database accordingly.
//     * @param args -
//     */
//    public static void main(String[] args) {
//        //Ensure that you use the Properties class to load values from the database.properties file
//        Properties dbConnection = new Properties();
//
//        //Preserve this input path
//        try (InputStream in = new FileInputStream("/Users/wangyanan/IdeaJavaProjects/lab2/cst8288-lab2-main/app/data/database.properties")){
//            try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
//                String out;
//                while ((out = br.readLine()) != null){
//                    System.out.println(out.toString());
//                }
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//        //Preserve this input path
//        try (InputStream in = new FileInputStream("/Users/wangyanan/IdeaJavaProjects/lab2/cst8288-lab2-main/app/data/bulk-import.csv")){
//            try(BufferedReader br = new BufferedReader(new InputStreamReader(in))){
//                String out;
//                while ((out = br.readLine()) != null){
//                    System.out.println(out.toString());
//                }
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//}
package org.cst8288Lab2;

import org.cst8288Lab2.DTO.*;
import org.cst8288Lab2.DAO.*;
import org.cst8288Lab2.Implement.CourseDAOImpl;
import org.cst8288Lab2.Implement.StudentCourseDAOImpl;
import org.cst8288Lab2.Implement.StudentDAOImpl;
import org.cst8288Lab2.util.Validator;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class App {

    public static void main(String[] args) throws IOException, SQLException {
        // Load database properties
        Properties dbProperties = new Properties();
        try (InputStream input = new FileInputStream("app/data/database.properties")) {
            dbProperties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Initialize DAOs
        StudentDAO studentDAO = new StudentDAOImpl();
        CourseDAO courseDAO = new CourseDAOImpl();
        StudentCourseDAO studentCourseDAO = new StudentCourseDAOImpl();

        // Read and process CSV file
        List<String> errorReport = new ArrayList<>();
        List<String> successReport = new ArrayList<>();
        int studentCount = 0;
        int courseCount = 0;
        int studentCourseCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("app/data/bulk-import.csv"))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // Skip header

                String[] values = line.split(",");
                if (values.length != 7) {
                    errorReport.add("Line " + lineNumber + ": Incorrect number of columns");
                    continue;
                }

                String studentId = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                String courseId = values[3].trim();
                String courseName = values[4].trim();
                String term = values[5].trim();
                String year = values[6].trim();

                try {
                    Validator.validateStudent(studentId, firstName, lastName);
                    Validator.validateCourse(courseId, courseName);
                    int termInt = Validator.validateTerm(term);
                    int yearInt = Validator.validateYear(year);

                    Student student = studentDAO.getStudentById(studentId);
                    if (student == null) {
                        studentDAO.addStudent(new Student(studentId, firstName, lastName));
                        studentCount++;
                        successReport.add("Added Student: " + studentId);
                    }

                    Course course = courseDAO.getCourseById(courseId);
                    if (course == null) {
                        courseDAO.addCourse(new Course(courseId, courseName));
                        courseCount++;
                        successReport.add("Added Course: " + courseId);
                    }

                    studentCourseDAO.addStudentCourse(new StudentCourse(
                            studentDAO.getStudentById(studentId),
                            courseDAO.getCourseById(courseId),
                            termInt,
                            yearInt
                    ));
                    studentCourseCount++;
                    successReport.add("Added StudentCourse: " + studentId + "-" + courseId);
                } catch (Exception e) {
                    errorReport.add("Line " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        // Write reports
        writeReport("app/data/import-report.md", successReport, studentCount, courseCount, studentCourseCount);
        writeReport("app/data/error-report.md", errorReport, 0, 0, 0);
    }

    private static void writeReport(String filePath, List<String> report, int studentCount, int courseCount, int studentCourseCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("# Report\n\n");
            writer.write("## Summary\n");
            writer.write("Date and Time: " + java.time.LocalDateTime.now() + "\n");
            if (filePath.contains("import-report.md")) {
                writer.write("Number of Students added: " + studentCount + "\n");
                writer.write("Number of Courses added: " + courseCount + "\n");
                writer.write("Number of StudentCourses added: " + studentCourseCount + "\n\n");
                writer.write("## Details\n");
            }
            for (String entry : report) {
                writer.write(entry + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
