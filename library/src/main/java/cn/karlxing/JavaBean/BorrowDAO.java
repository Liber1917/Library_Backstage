package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
    private Connection connection;

    public BorrowDAO(Connection connection) {
        this.connection = connection;
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

    public boolean addBorrow(BorrowPO borrow) {
        String sql = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, borrow.getStudentID());
            pstmt.setInt(2, borrow.getBookID());
            pstmt.setInt(3, borrow.getBorrowDate());
            pstmt.setInt(4, borrow.getReturnDate());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBorrow(BorrowPO borrow) {
        String query = "UPDATE borrows SET student_id = ?, book_id = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrow.getStudentID());
            ps.setInt(2, borrow.getBookID());
            ps.setInt(3, borrow.getBorrowDate());
            ps.setInt(4, borrow.getReturnDate());
            ps.setInt(5, borrow.getId());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBorrow(int id) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
}