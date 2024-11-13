package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private Connection conn;

    // Constructor to initialize the connection
    public StudentDAO(Connection conn) {
        this.conn = conn;
    }

    public void addStudent(StudentPO student) {
        try {
            String sqlStr = "INSERT INTO student(Sid, Sname, Sdept, Smajor, Sborrowed) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = conn.prepareStatement(sqlStr);

            prepStmt.setInt(1, student.getID());
            prepStmt.setString(2, student.getName());
            prepStmt.setString(3, student.getDept());
            prepStmt.setString(4, student.getMajor());
            prepStmt.setInt(5, student.getBorrowed());
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("addStudent error: " + e);
        }
    }

    public void addStudents(ArrayList<StudentPO> students) {
        for (StudentPO student : students) {
            addStudent(student);
        }
    }

    public void deleteStudent(StudentPO student) {
        try {
            if (conn != null) {
                String sqlStr = "DELETE FROM student WHERE Sid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);
                prepStmt.setInt(1, student.getID());
                prepStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("deleteStudent error: " + e);
        }
    }

    public void deleteStudents(ArrayList<StudentPO> students) {
        for (StudentPO student : students) {
            deleteStudent(student);
        }
    }

    public void updateStudent(StudentPO student) {
        try {
            if (conn != null) {
                String sqlStr = "UPDATE student SET Sname = ?, Sdept = ?, Smajor = ?, Sborrowed = ? WHERE Sid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);

                prepStmt.setString(1, student.getName());
                prepStmt.setString(2, student.getDept());
                prepStmt.setString(3, student.getMajor());
                prepStmt.setInt(4, student.getBorrowed());
                prepStmt.setInt(5, student.getID());
                prepStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateStudent error: " + e);
        }
    }

    public void updateStudents(ArrayList<StudentPO> students) {
        for (StudentPO student : students) {
            updateStudent(student);
        }
    }

    public StudentPO queryStudentByKey(int id) {
        StudentPO student = null;
        try {
            if (conn != null) {
                String sqlStr = "SELECT * FROM student WHERE Sid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);
                prepStmt.setInt(1, id);
                ResultSet rs = prepStmt.executeQuery();

                if (rs.next()) {
                    student = new StudentPO();
                    student.setID(rs.getInt("Sid"));
                    student.setName(rs.getString("Sname"));
                    student.setDept(rs.getString("Sdept"));
                    student.setMajor(rs.getString("Smajor"));
                    student.setBorrowed(rs.getInt("Sborrowed"));
                }
            }
        } catch (Exception e) {
            System.out.println("queryStudentByKey error: " + e);
        }

        return student;
    }

    public ArrayList<StudentPO> queryStudents() {
        ArrayList<StudentPO> students = new ArrayList<>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM student ORDER BY Sid");

                while (rs.next()) {
                    StudentPO student = new StudentPO();
                    student.setID(rs.getInt("Sid"));
                    student.setName(rs.getString("Sname"));
                    student.setDept(rs.getString("Sdept"));
                    student.setMajor(rs.getString("Smajor"));
                    student.setBorrowed(rs.getInt("Sborrowed"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            System.out.println("queryStudents error: " + e);
        }

        return students;
    }
}
