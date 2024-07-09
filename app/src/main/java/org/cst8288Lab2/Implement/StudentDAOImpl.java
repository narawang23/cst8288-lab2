package org.cst8288Lab2.Implement;

import org.cst8288Lab2.DAO.StudentDAO;
import org.cst8288Lab2.DTO.Student;
import org.cst8288Lab2.DataSource.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private Connection connection;

    public StudentDAOImpl() {
        this.connection = DatabaseUtils.getConnection();
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                ));
            }
        }
        return students;
    }

    @Override
    public Student getStudentById(String studentId) throws SQLException {
        String sql = "SELECT * FROM Student WHERE studentId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("studentId"),
                            rs.getString("firstName"),
                            rs.getString("lastName")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Student (studentId, firstName, lastName) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE Student SET firstName = ?, lastName = ? WHERE studentId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getStudentId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM Student WHERE studentId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.executeUpdate();
        }
    }
}
