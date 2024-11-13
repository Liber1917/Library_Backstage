package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BorrowDAO {
    private Connection connection;

    public BorrowDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new borrow record
    public boolean addBorrow(BorrowPO borrow) {
        String query = "INSERT INTO Borrow (Sid, Bid, borrowDate, returnDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, borrow.getStudentID());
            ps.setInt(2, borrow.getBookID());
            ps.setInt(3, borrow.getBorrowDate());
            ps.setInt(4, borrow.getReturnDate());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        borrow.setId(generatedKeys.getInt(1));
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to convert six-digit date integer to java.sql.Date
    private Date convertToDate(int dateInt) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, dateInt / 10000);
        calendar.set(Calendar.MONTH, (dateInt % 10000) / 100 - 1); // Calendar.MONTH is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, dateInt % 100);
        return new Date(calendar.getTimeInMillis());
    }

    // Helper method to convert java.sql.Date to six-digit date integer
    private int getDateInt(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) % 10000;
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year * 10000 + month * 100 + day;
    }

    // Method to update a borrow record
    public boolean updateBorrow(BorrowPO borrow) {
        String query = "UPDATE Borrow SET borrowDate = ?, returnDate = ? WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, convertToDate(borrow.getBorrowDate()));
            ps.setDate(2, convertToDate(borrow.getReturnDate()));
            ps.setInt(3, borrow.getStudentID());
            ps.setInt(4, borrow.getBookID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a borrow record
    public boolean deleteBorrow(int sid, int bid) {
        String query = "DELETE FROM Borrow WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sid);
            ps.setInt(2, bid);
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
                borrow.setId(rs.getInt("id"));
                borrow.setStudentID(rs.getInt("Sid"));
                borrow.setBookID(rs.getInt("Bid"));
                borrow.setBorrowDate(getDateInt(rs.getDate("borrowDate")));
                borrow.setReturnDate(getDateInt(rs.getDate("returnDate")));
                borrowList.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowList;
    }

    // Method to check if a borrow record exists
    public boolean existsBorrow(int sid, int bid) {
        String query = "SELECT COUNT(*) FROM Borrow WHERE Sid = ? AND Bid = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, sid);
            ps.setInt(2, bid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}