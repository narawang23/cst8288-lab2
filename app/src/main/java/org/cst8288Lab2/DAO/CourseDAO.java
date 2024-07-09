package org.cst8288Lab2.DAO;

import java.sql.SQLException;
import java.util.List;
import org.cst8288Lab2.DTO.Course;

public interface CourseDAO {
    List<Course> getAllCourses() throws SQLException;
    Course getCourseById(String courseId) throws SQLException;
    void addCourse(Course course) throws SQLException;
    void updateCourse(Course course) throws SQLException;
    void deleteCourse(String courseId) throws SQLException;
}
