package JavaBean;

import cn.karlxing.JavaBean.BookDAO;
import cn.karlxing.JavaBean.BookPO;

import java.sql.*;
import java.util.ArrayList;

public class BookDAOTester {
    private Connection connection; // 使用连接
    private BookDAO dao;

    public BookDAOTester() {
        String user = "root";
        String password = ""; // 确保这里匹配 MySQL 中 root 用户的密码
        String url = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

        try {
            connection = DriverManager.getConnection(url, user, password);
            dao = new BookDAO(connection); // 初始化BookDAO，并传入MySQL连接
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
        String sql = "CREATE TABLE book(Bid INT PRIMARY KEY, Btitle VARCHAR(100), Bauthor VARCHAR(100), Bversion VARCHAR(50))";
        try (Statement stmt = connection.createStatement()) { // 使用try-with-resources
            stmt.execute(sql);
            System.out.println("Created table book");
        } catch (SQLException e) {
            System.out.println("Error creating table book: " + e.getMessage());
        }
    }

    private void dropTable() {
        try (Statement stmt = connection.createStatement()) { // 使用try-with-resources
            stmt.execute("DROP TABLE IF EXISTS book");
            System.out.println("Dropped table book");
        } catch (SQLException e) {
            System.out.println("Error dropping table book: " + e.getMessage());
        }
    }

    private void insertInitRecords() {
        ArrayList<BookPO> books = new ArrayList<>();

        // 创建初始书籍记录
        books.add(createBook(101, "Effective Java", "Joshua Bloch", "3rd Edition"));
        books.add(createBook(102, "Java: The Complete Reference", "Herbert Schildt", "11th Edition"));
        books.add(createBook(103, "Clean Code", "Robert C. Martin", "1st Edition"));

        for (BookPO book : books) {
            dao.addBook(book); // 添加每个书籍记录
        }
        System.out.println("Inserted 3 initial records");
        System.out.println("== Records initialized successfully ==");

        testQueryRecords();
    }

    private BookPO createBook(int bid, String title, String author, String version) {
        BookPO book = new BookPO();
        book.setId(bid);
        book.setTitle(title);
        book.setAuthor(author);
        book.setVersion(version);
        return book;
    }

    private void testQueryRecords() {
        System.out.println("Bid " + "  Title  " + "  Author  " + "  Version");
        ArrayList<BookPO> list = dao.queryBooks(); // 查询书籍记录
        if (list != null && !list.isEmpty()) {
            for (BookPO one : list) {
                System.out.println(one.toString());
            }
        } else {
            System.out.println("No records found.");
        }
    }

    private void testInsertRecord() {
        BookPO book = createBook(88888, "John Doe", "Unknown Author", "1st Edition");
        dao.addBook(book); // 添加单本书籍记录

        System.out.println();
        System.out.println("== After inserting a new book record =="); // 输出结果
        testQueryRecords();
    }

    private void testUpdateRecord() {
        BookPO book = dao.queryBookByKey(88888); // 查询书籍记录
        if (book != null) {
            book.setTitle("Updated Title"); // 更新标题
            dao.updateBook(book); // 更新书籍记录

            System.out.println();
            System.out.println("== After updating a book record =="); // 输出结果
            testQueryRecords();
        } else {
            System.out.println("No book found with Bid 88888 for update.");
        }
    }

    private void testDeleteRecord() {
        BookPO book = dao.queryBookByKey(88888); // 查询书籍记录
        if (book != null) {
            dao.deleteBook(book); // 删除书籍记录
            System.out.println();
            System.out.println("== After deleting a book record =="); // 输出结果
            testQueryRecords();
        } else {
            System.out.println("No book found with Bid 88888 for deletion.");
        }
    }

    public static void main(String[] args) {
        BookDAOTester daoTester = new BookDAOTester(); // 创建测试实例
        daoTester.dropTable(); // 删除表
        daoTester.createTable(); // 创建表
        daoTester.insertInitRecords(); // 插入初始记录
        daoTester.testInsertRecord(); // 测试插入记录
        daoTester.testUpdateRecord(); // 测试更新记录
        daoTester.testDeleteRecord(); // 测试删除记录
        daoTester.closeConnection(); // 关闭连接
    }
}
