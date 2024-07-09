package org.cst8288Lab2.DAO;
import org.cst8288Lab2.DTO.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    List<Student> getAllStudents() throws Exception;
    Student getStudentById(String studentId)throws SQLException;
    void addStudent(Student student)throws SQLException;
    void updateStudent(Student student)throws SQLException;
    void deleteStudent(String student)throws SQLException;
}
