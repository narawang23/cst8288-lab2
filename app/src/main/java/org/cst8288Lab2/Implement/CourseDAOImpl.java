package org.cst8288Lab2.Implement;

import org.cst8288Lab2.DAO.CourseDAO;
import org.cst8288Lab2.DTO.Course;
import org.cst8288Lab2.DataSource.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    private Connection connection;

    public CourseDAOImpl() {
        this.connection = DatabaseUtils.getConnection();
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getString("courseId"),
                        rs.getString("courseName")
                ));
            }
        }
        return courses;
    }

    @Override
    public Course getCourseById(String courseId) throws SQLException {
        String sql = "SELECT * FROM Course WHERE courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getString("courseId"),
                            rs.getString("courseName")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Course (courseId, courseName) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Course SET courseName = ? WHERE courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setString(2, course.getCourseId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteCourse(String courseId) throws SQLException {
        String sql = "DELETE FROM Course WHERE courseId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.executeUpdate();
        }
    }
}
