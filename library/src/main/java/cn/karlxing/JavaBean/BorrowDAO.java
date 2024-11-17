package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
    private Connection connection;

    public BorrowDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new borrow record with specified ID
    public boolean addBorrow(BorrowPO borrow) {
        String sql = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, borrow.getStudentID());
            pstmt.setInt(2, borrow.getBookID());
            pstmt.setInt(3, borrow.getBorrowDate());
            pstmt.setInt(4, borrow.getReturnDate());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record added successfully");
                return true;
            } else {
                System.out.println("Failed to add record");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a borrow record by ID
    public BorrowPO getBorrowById(int id) {
        String query = "SELECT * FROM Borrow WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BorrowPO borrow = new BorrowPO();
                borrow.setId(rs.getInt("id"));
                borrow.setStudentID(rs.getInt("Sid"));
                borrow.setBookID(rs.getInt("Bid"));
                borrow.setBorrowDate(rs.getInt("borrowDate"));
                borrow.setReturnDate(rs.getInt("returnDate"));
                return borrow;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update a borrow record
    public boolean updateBorrow(BorrowPO borrow) {
        String query = "UPDATE Borrow SET Sid = ?, Bid = ?, borrowDate = ?, returnDate = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrow.getStudentID());
            ps.setInt(2, borrow.getBookID());
            ps.setInt(3, borrow.getBorrowDate());
            ps.setInt(4, borrow.getReturnDate());
            ps.setInt(5, borrow.getId()); // Use the specified ID
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a borrow record by ID
    public boolean deleteBorrow(int id) {
        String query = "DELETE FROM Borrow WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all borrow records
    public List<BorrowPO> getAllBorrows() {
        List<BorrowPO> borrows = new ArrayList<>();
        String sql = "SELECT * FROM borrows";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BorrowPO borrow = new BorrowPO();
                borrow.setId(rs.getInt("id"));
                borrow.setStudentID(rs.getInt("student_id"));
                borrow.setBookID(rs.getInt("book_id"));
                borrow.setBorrowDate(rs.getInt("borrow_date"));
                borrow.setReturnDate(rs.getInt("return_date"));
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    // Method to check if a borrow record exists
    public boolean existsBorrow(int studentID, int bookID) {
        String query = "SELECT COUNT(*) FROM Borrow WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentID);
            ps.setInt(2, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BorrowPO getBorrow(int id) {
        String sql = "SELECT * FROM borrows WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BorrowPO borrow = new BorrowPO();
                borrow.setId(rs.getInt("id"));
                borrow.setStudentID(rs.getInt("student_id"));
                borrow.setBookID(rs.getInt("book_id"));
                borrow.setBorrowDate(rs.getInt("borrow_date"));
                borrow.setReturnDate(rs.getInt("return_date"));
                return borrow;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}