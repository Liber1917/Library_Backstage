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
        String sql = "SELECT * FROM borrow WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
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

    public boolean addBorrow(BorrowPO borrow) {
        String sqlMaxId = "SELECT MAX(id) FROM borrow";
        String sqlInsert = "INSERT INTO borrow (id, Sid, Bid, borrowDate, returnDate) VALUES (?, ?, ?, ?, ?)";
        int newId = 0;

        try (Connection conn = connection;
             PreparedStatement pstmtMax = conn.prepareStatement(sqlMaxId);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {

            ResultSet rs = pstmtMax.executeQuery();
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // 获取最大 id 并加 1
            } else {
                newId = 1; // 如果表中没有记录，从 1 开始
            }

            pstmtInsert.setInt(1, newId);
            pstmtInsert.setInt(2, borrow.getStudentID());
            pstmtInsert.setInt(3, borrow.getBookID());
            pstmtInsert.setInt(4, borrow.getBorrowDate());
            pstmtInsert.setInt(5, borrow.getReturnDate());
            int affectedRows = pstmtInsert.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // 唯一性约束违反的错误代码
                System.out.println("Error: This book has already been borrowed by the student.");
                return false;
            } else {
                e.printStackTrace();
                System.out.println("Error adding borrow: " + e.getMessage());
                return false;
            }
        }
    }

    public boolean updateBorrow(BorrowPO borrow) {
        String query = "UPDATE borrow SET Sid = ?, Bid = ?, borrowDate = ?, returnDate = ? WHERE id = ?";
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
        String query = "DELETE FROM borrow WHERE id = ?";
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
        String sql = "SELECT * FROM borrow";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BorrowPO borrow = new BorrowPO();
                borrow.setId(rs.getInt("id"));
                borrow.setStudentID(rs.getInt("Sid"));
                borrow.setBookID(rs.getInt("Bid"));
                borrow.setBorrowDate(rs.getInt("borrowDate"));
                borrow.setReturnDate(rs.getInt("returnDate"));
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching borrow records", e);
        }
        return borrows;
    }
}