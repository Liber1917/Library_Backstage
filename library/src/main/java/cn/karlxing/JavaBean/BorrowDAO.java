package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
    private Connection connection;

    public BorrowDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new borrow record
    public boolean addBorrow(BorrowPO borrow) {
        String query = "INSERT INTO Borrow (Sid, Bid, borrowDate, returnDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrow.getStudentID());
            ps.setInt(2, borrow.getBookID());
            ps.setInt(3, borrow.getBorrowDate());
            ps.setInt(4, borrow.getReturnDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a borrow record by student ID and book ID
    public BorrowPO getBorrow(int Sid, int Bid) {
        String query = "SELECT * FROM Borrow WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Sid);
            ps.setInt(2, Bid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BorrowPO borrow = new BorrowPO();
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
        String query = "UPDATE Borrow SET borrowDate = ?, returnDate = ? WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrow.getBorrowDate());
            ps.setInt(2, borrow.getReturnDate());
            ps.setInt(3, borrow.getStudentID());
            ps.setInt(4, borrow.getBookID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a borrow record
    public boolean deleteBorrow(int Sid, int Bid) {
        String query = "DELETE FROM Borrow WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Sid);
            ps.setInt(2, Bid);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get all borrow records
    public List<BorrowPO> getAllBorrows() {
        List<BorrowPO> borrowList = new ArrayList<>();
        String query = "SELECT * FROM Borrow";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                BorrowPO borrow = new BorrowPO();
                borrow.setStudentID(rs.getInt("Sid"));
                borrow.setBookID(rs.getInt("Bid"));
                borrow.setBorrowDate(rs.getInt("borrowDate"));
                borrow.setReturnDate(rs.getInt("returnDate"));
                borrowList.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowList;
    }
}
