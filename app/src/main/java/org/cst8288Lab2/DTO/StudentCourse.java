package org.cst8288Lab2.DTO;

public class StudentCourse {
    private Student student;
    private Course course;
    private int term;
    private int year;

    public StudentCourse(Student student, Course course, int term, int year) {
        this.student = student;
        this.course = course;
        this.term = term;
        this.year = year;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
