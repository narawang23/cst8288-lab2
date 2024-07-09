package org.cst8288Lab2.DAO;

import java.sql.SQLException;
import java.util.List;
import org.cst8288Lab2.DTO.StudentCourse;

public interface StudentCourseDAO {
    List<StudentCourse> getAllStudentCourses() throws SQLException;
    StudentCourse getStudentCourseById(int studentId, String courseId) throws SQLException;
    void addStudentCourse(StudentCourse studentCourse) throws SQLException;
    void updateStudentCourse(StudentCourse studentCourse) throws SQLException;
    void deleteStudentCourse(int studentId, String courseId) throws SQLException;
}

