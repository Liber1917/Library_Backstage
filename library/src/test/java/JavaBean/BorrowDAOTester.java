package cn.karlxing.test;

import cn.karlxing.JavaBean.BorrowDAO;
import cn.karlxing.JavaBean.BorrowPO;
import cn.karlxing.JavaBean.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAOTester {

    private Connection connection;
    private BorrowDAO dao;

    public BorrowDAOTester() {
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

        try {
            connection = DriverManager.getConnection(url, user, password);
            dao = new BorrowDAO(connection); // Initialize BorrowDAO with MySQL connection
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
        String sql = "CREATE TABLE Borrow (id INT PRIMARY KEY, Sid INT, Bid INT, borrowDate INT, returnDate INT)";
        try (Statement stmt = connection.createStatement()) { // Use try-with-resources
            stmt.execute(sql);
            System.out.println("Created table Borrow");
        } catch (SQLException e) {
            System.out.println("Error creating table Borrow: " + e.getMessage());
        }
    }

    private void dropTable() {
        try (Statement stmt = connection.createStatement()) { // Use try-with-resources
            stmt.execute("DROP TABLE IF EXISTS Borrow");
            System.out.println("Dropped table Borrow");
        } catch (SQLException e) {
            System.out.println("Error dropping table Borrow: " + e.getMessage());
        }
    }

    private void insertInitRecords() {
        ArrayList<BorrowPO> borrows = new ArrayList<>();

        // Create initial borrow records
        BorrowPO borrow1 = new BorrowPO();
        borrow1.setId(100); // 手动设置唯一的ID
        borrow1.setStudentID(1);
        borrow1.setBookID(101);
        borrow1.setBorrowDate(20241130);
        borrow1.setReturnDate(0);

        BorrowPO borrow2 = new BorrowPO();
        borrow2.setId(200); // 手动设置唯一的ID
        borrow2.setStudentID(2);
        borrow2.setBookID(102);
        borrow2.setBorrowDate(20241130);
        borrow2.setReturnDate(0);

        dao.addBorrow(borrow1); // Add first borrow record
        dao.addBorrow(borrow2); // Add second borrow record
        System.out.println("Inserted 2 initial records");
        System.out.println("== Records initialized successfully ==");

        testQueryRecords();
    }

    private void testQueryRecords() {
        System.out.println("ID " + "  Student ID " + "  Book ID " + "  Borrow Date " + "  Return Date");
        List<BorrowPO> list = dao.getAllBorrows(); // Query borrow records
        if (list != null && !list.isEmpty()) {
            for (BorrowPO one : list) {
                System.out.println(one.toString());
            }
        } else {
            System.out.println("No records found.");
        }
    }

    private void testInsertRecord() {
        BorrowPO borrow = new BorrowPO();
        borrow.setStudentID(3);
        borrow.setBookID(103);
        borrow.setBorrowDate(20241130);
        borrow.setReturnDate(0);
        dao.addBorrow(borrow); // Add a single borrow record

        System.out.println();
        System.out.println("== After inserting a new borrow record =="); // Output result
        testQueryRecords();
    }

    private void testUpdateRecord() {
        BorrowPO borrow = dao.getBorrowById(100); // Query borrow record
        if (borrow != null) {
            borrow.setReturnDate(20241230); // Update return date
            dao.updateBorrow(borrow); // Update borrow record

            System.out.println();
            System.out.println("== After updating a borrow record =="); // Output result
            testQueryRecords();
        } else {
            System.out.println("No borrow found with Book ID 103 for update.");
        }
    }

    private void testDeleteRecord() {
        BorrowPO borrow = dao.getBorrowById(100); // Query borrow record
        if (borrow != null) {
            dao.deleteBorrow(borrow.getId()); // Delete borrow record
            System.out.println();
            System.out.println("== After deleting a borrow record =="); // Output result
            testQueryRecords();
        } else {
            System.out.println("No borrow found with Book ID 103 for deletion.");
        }
    }

    public static void main(String[] args) {
        BorrowDAOTester daoTester = new BorrowDAOTester(); // Create an instance of the tester
        daoTester.dropTable(); // Drop table
        daoTester.createTable(); // Create table
        daoTester.insertInitRecords(); // Insert initial records
        daoTester.testInsertRecord(); // Test inserting records
        daoTester.testUpdateRecord(); // Test updating records
        daoTester.testDeleteRecord(); // Test deleting records
        daoTester.closeConnection(); // Close connection
    }
}