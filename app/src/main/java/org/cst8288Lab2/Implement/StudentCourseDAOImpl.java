package org.cst8288Lab2.Implement;

import org.cst8288Lab2.DAO.StudentCourseDAO;
import org.cst8288Lab2.DTO.Course;
import org.cst8288Lab2.DTO.Student;
import org.cst8288Lab2.DTO.StudentCourse;
import org.cst8288Lab2.DataSource.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDAOImpl implements StudentCourseDAO {

    private Connection connection;

    public StudentCourseDAOImpl() {
        this.connection = DatabaseUtils.getConnection();
    }

    @Override
    public List<StudentCourse> getAllStudentCourses() throws SQLException {
        List<StudentCourse> results = new ArrayList<>();
        String sql = "SELECT sc.studentId, sc.courseId, sc.term, sc.year, " +
                "s.firstName, s.lastName, c.courseName " +
                "FROM StudentCourse sc " +
                "JOIN Student s ON sc.studentId = s.studentId " +
                "JOIN Course c ON sc.courseId = c.courseId";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );
                Course course = new Course(
                        rs.getString("courseId"),
                        rs.getString("courseName")
                );
                results.add(new StudentCourse(student, course, rs.getInt("term"), rs.getInt("year")));
            }
        }
        return results;
    }

    @Override
    public StudentCourse getStudentCourseById(int studentId, String courseId) throws SQLException {
        String sql = "SELECT sc.studentId, sc.courseId, sc.term, sc.year, " +
                "s.firstName, s.lastName, c.courseName " +
                "FROM StudentCourse sc " +
                "JOIN Student s ON sc.studentId = s.studentId " +
                "JOIN Course c ON sc.courseId = c.courseId " +
                "WHERE sc.studentId = ? AND sc.courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student(
                            rs.getString("studentId"),
                            rs.getString("firstName"),
                            rs.getString("lastName")
                    );
                    Course course = new Course(
                            rs.getString("courseId"),
                            rs.getString("courseName")
                    );
                    return new StudentCourse(student, course, rs.getInt("term"), rs.getInt("year"));
                }
            }
        }
        return null;
    }

    @Override
    public void addStudentCourse(StudentCourse studentCourse) throws SQLException {
        String sql = "INSERT INTO StudentCourse (studentId, courseId, term, year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(studentCourse.getStudent().getStudentId()));
            stmt.setString(2, studentCourse.getCourse().getCourseId());
            stmt.setInt(3, studentCourse.getTerm());
            stmt.setInt(4, studentCourse.getYear());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateStudentCourse(StudentCourse studentCourse) throws SQLException {
        String sql = "UPDATE StudentCourse SET term = ?, year = ? WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentCourse.getTerm());
            stmt.setInt(2, studentCourse.getYear());
            stmt.setInt(3, Integer.parseInt(studentCourse.getStudent().getStudentId()));
            stmt.setString(4, studentCourse.getCourse().getCourseId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteStudentCourse(int studentId, String courseId) throws SQLException {
        String sql = "DELETE FROM StudentCourse WHERE studentId = ? AND courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        }
    }
}
