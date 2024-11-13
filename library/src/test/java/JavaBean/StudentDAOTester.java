package JavaBean;

import java.sql.*;
import java.util.ArrayList;

import cn.karlxing.JavaBean.StudentDAO;
import cn.karlxing.JavaBean.StudentPO;

public class StudentDAOTester {
    private Connection connection; // Using Connection instead of DatabaseManager
    private StudentDAO dao;

    public StudentDAOTester() {
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

        try {
            connection = DriverManager.getConnection(url, user, password);
            dao = new StudentDAO(connection); // Initialize StudentDAO with MySQL connection
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Closed connection successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE student(Sid INT PRIMARY KEY, Sname VARCHAR(20), Sdept VARCHAR(30), Smajor VARCHAR(30), Sborrowed INT)";
        try (Statement stmt = connection.createStatement()) { // Use try-with-resources
            stmt.execute(sql);
            System.out.println("Created table student");
        } catch (SQLException e) {
            System.out.println("Error creating table student: " + e.getMessage());
        }
    }

    private void dropTable() {
        try (Statement stmt = connection.createStatement()) { // Use try-with-resources
            stmt.execute("DROP TABLE IF EXISTS student");
            System.out.println("Dropped table student");
        } catch (SQLException e) {
            System.out.println("Error dropping table student: " + e.getMessage());
        }
    }

    private void insertInitRecords() {
        ArrayList<StudentPO> students = new ArrayList<>();

        // Create initial student records
        students.add(createStudent(95001, "San Zhang", "Informatics", "Industrial Intelligence"));
        students.add(createStudent(95002, "Li Si", "Computer Science", "Software Engineering"));
        students.add(createStudent(95003, "Wang Wu", "Mathematics", "Statistics", 1));

        dao.addStudents(students); // Add student records
        System.out.println("Inserted 3 initial records");
        System.out.println("== Records initialized successfully ==");

        testQueryRecords();
    }

    private StudentPO createStudent(int id, String name, String dept, String major) {
        StudentPO student = new StudentPO();
        student.setID(id);
        student.setName(name);
        student.setDept(dept);
        student.setMajor(major);
        student.setBorrowed(0); // Initialize borrowed to 0 or any other default value
        return student;
    }

    private StudentPO createStudent(int id, String name, String dept, String major, int borrowed) {
        StudentPO student = new StudentPO();
        student.setID(id);
        student.setName(name);
        student.setDept(dept);
        student.setMajor(major);
        student.setBorrowed(borrowed); // Initialize borrowed to 0 or any other default value
        return student;
    }

    private void testQueryRecords() {
        System.out.println("ID " + "  Name  " + "  Department  " + "  Major " + "  Borrowed");
        ArrayList<StudentPO> list = dao.queryStudents(); // Query student records
        if (list != null && !list.isEmpty()) {
            for (StudentPO one : list) {
                System.out.println(one.toString());
            }
        } else {
            System.out.println("No records found.");
        }
    }

    private void testInsertRecord() {
        StudentPO student = createStudent(88888, "John Doe", "Science", "Biology");
        dao.addStudent(student); // Add a single student record

        System.out.println();
        System.out.println("== After inserting a new student record =="); // Output result
        testQueryRecords();
    }

    private void testUpdateRecord() {
        StudentPO student = dao.queryStudentByKey(88888); // Query student record
        if (student != null) {
            student.setName("Updated Name"); // Update name
            dao.updateStudent(student); // Update student record

            System.out.println();
            System.out.println("== After updating a student record =="); // Output result
            testQueryRecords();
        } else {
            System.out.println("No student found with ID 88888 for update.");
        }
    }

    private void testDeleteRecord() {
        StudentPO student = dao.queryStudentByKey(88888); // Query student record
        if (student != null) {
            dao.deleteStudent(student); // Delete student record
            System.out.println();
            System.out.println("== After deleting a student record =="); // Output result
            testQueryRecords();
        } else {
            System.out.println("No student found with ID 88888 for deletion.");
        }
    }

    public static void main(String[] args) {
        StudentDAOTester daoTester = new StudentDAOTester(); // Create an instance of the tester
        daoTester.dropTable(); // Drop table
        daoTester.createTable(); // Create table
        daoTester.insertInitRecords(); // Insert initial records
        daoTester.testInsertRecord(); // Test inserting records
        daoTester.testUpdateRecord(); // Test updating records
        daoTester.testDeleteRecord(); // Test deleting records
        daoTester.closeConnection(); // Close connection
    }
}
