package cn.karlxing.JavaBean;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    private Connection conn;

    // 构造函数初始化连接
    public BookDAO(Connection conn) {
        this.conn = conn;
    }

    // 添加单个书籍记录
    public void addBook(BookPO book) {
        try {
            String sqlStr = "INSERT INTO book (Bid, Btitle, Bauthor, Bversion) VALUES (?, ?, ?, ?)";
            PreparedStatement prepStmt = conn.prepareStatement(sqlStr);

            prepStmt.setInt(1, book.getId());
            prepStmt.setString(2, book.getTitle());
            prepStmt.setString(3, book.getAuthor());
            prepStmt.setString(4, book.getVersion());
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("addBook error: " + e);
        }
    }

    // 批量添加书籍记录
    public void addBooks(ArrayList<BookPO> books) {
        for (BookPO book : books) {
            addBook(book);
        }
    }

    // 删除单个书籍记录
    public void deleteBook(BookPO book) {
        try {
            if (conn != null) {
                String sqlStr = "DELETE FROM book WHERE Bid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);
                prepStmt.setInt(1, book.getId());
                prepStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("deleteBook error: " + e);
        }
    }

    // 批量删除书籍记录
    public void deleteBooks(ArrayList<BookPO> books) {
        for (BookPO book : books) {
            deleteBook(book);
        }
    }

    // 更新单个书籍记录
    public void updateBook(BookPO book) {
        try {
            if (conn != null) {
                String sqlStr = "UPDATE book SET Btitle = ?, Bauthor = ?, Bversion = ? WHERE Bid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);

                prepStmt.setString(1, book.getTitle());
                prepStmt.setString(2, book.getAuthor());
                prepStmt.setString(3, book.getVersion());
                prepStmt.setInt(4, book.getId());
                prepStmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateBook error: " + e);
        }
    }

    // 批量更新书籍记录
    public void updateBooks(ArrayList<BookPO> books) {
        for (BookPO book : books) {
            updateBook(book);
        }
    }

    // 根据主键查询书籍记录
    public BookPO queryBookByKey(int id) {
        BookPO book = null;
        try {
            if (conn != null) {
                String sqlStr = "SELECT * FROM book WHERE Bid = ?";
                PreparedStatement prepStmt = conn.prepareStatement(sqlStr);
                prepStmt.setInt(1, id);
                ResultSet rs = prepStmt.executeQuery();

                if (rs.next()) {
                    book = new BookPO();
                    book.setId(rs.getInt("Bid"));
                    book.setTitle(rs.getString("Btitle"));
                    book.setAuthor(rs.getString("Bauthor"));
                    book.setVersion(rs.getString("Bversion"));
                }
            }
        } catch (Exception e) {
            System.out.println("queryBookByKey error: " + e);
        }

        return book;
    }

    // 查询所有书籍记录
    public ArrayList<BookPO> queryBooks() {
        ArrayList<BookPO> books = new ArrayList<>();
        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM book ORDER BY Bid");

                while (rs.next()) {
                    BookPO book = new BookPO();
                    book.setId(rs.getInt("Bid"));
                    book.setTitle(rs.getString("Btitle"));
                    book.setAuthor(rs.getString("Bauthor"));
                    book.setVersion(rs.getString("Bversion"));
                    books.add(book);
                }
            }
        } catch (Exception e) {
            System.out.println("queryBooks error: " + e);
        }

        return books;
    }
}
