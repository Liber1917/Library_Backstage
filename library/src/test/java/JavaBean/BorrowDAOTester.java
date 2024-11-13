package JavaBean;

import cn.karlxing.JavaBean.BorrowDAO;
import cn.karlxing.JavaBean.BorrowPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowDAOTester {
    private Connection connection;
    private BorrowDAO dao;

    public BorrowDAOTester() {
        String user = "root";
        String password = ""; // 请确保与 MySQL root 用户密码一致
        String url = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";

        try {
            connection = DriverManager.getConnection(url, user, password);
            dao = new BorrowDAO(connection); // 使用 MySQL 连接初始化 BorrowDAO
        } catch (SQLException e) {
            System.out.println("连接失败: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("成功关闭连接。");
            }
        } catch (SQLException e) {
            System.out.println("关闭连接失败: " + e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE Borrow(Sid INT, Bid INT, borrowDate INT, returnDate INT, PRIMARY KEY (Sid, Bid))";
        try (var stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("创建表 Borrow 成功。");
        } catch (SQLException e) {
            System.out.println("创建表 Borrow 时出错: " + e.getMessage());
        }
    }

    private void dropTable() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Borrow");
            System.out.println("删除表 Borrow 成功。");
        } catch (SQLException e) {
            System.out.println("删除表 Borrow 时出错: " + e.getMessage());
        }
    }

    private void insertInitRecords() {
        ArrayList<BorrowPO> borrows = new ArrayList<>();

        // 创建初始借阅记录
        borrows.add(createBorrow(1, 101, 20231110, 20231210));
        borrows.add(createBorrow(2, 102, 20231111, 20231211));
        borrows.add(createBorrow(3, 103, 20231112, 20231212));

        for (BorrowPO borrow : borrows) {
            dao.addBorrow(borrow);
        }
        System.out.println("插入初始借阅记录成功。");

        testQueryRecords();
    }

    private BorrowPO createBorrow(int sid, int bid, int borrowDate, int returnDate) {
        BorrowPO borrow = new BorrowPO();
        borrow.setStudentID(sid);
        borrow.setBookID(bid);
        borrow.setBorrowDate(borrowDate);
        borrow.setReturnDate(returnDate);
        return borrow;
    }

    private void testQueryRecords() {
        System.out.println("Sid " + "  Bid  " + "  Borrow Date  " + "  Return Date ");
        var list = dao.getAllBorrows();
        if (list != null && !list.isEmpty()) {
            for (BorrowPO one : list) {
                System.out.println(one);
            }
        } else {
            System.out.println("未找到任何借阅记录。");
        }
    }

    private void testInsertBorrowOnlyRecord() {
        // 插入仅包含借书日期的记录，不设置还书日期
        BorrowPO borrow = createBorrow(5, 105, 20231113, 0); // 设置returnDate为0表示空值
        dao.addBorrow(borrow);

        System.out.println();
        System.out.println("== 插入仅包含借书日期的记录后 ==");
        testQueryRecords();
    }

    private void testReturnBook() {
        // 更新一条记录的还书日期
        BorrowPO borrow = dao.getBorrow(5, 105);
        if (borrow != null) {
            borrow.setReturnDate(20231215); // 设置还书日期
            dao.updateBorrow(borrow);

            System.out.println();
            System.out.println("== 更新还书日期后 ==");
            testQueryRecords();
        } else {
            System.out.println("未找到 Sid=5 和 Bid=105 的借阅记录用于更新。");
        }
    }

    private void testInsertRecord() {
        BorrowPO borrow = createBorrow(4, 104, 20231113, 20231213);
        dao.addBorrow(borrow);

        System.out.println();
        System.out.println("== 插入新借阅记录后 ==");
        testQueryRecords();
    }

    private void testUpdateRecord() {
        BorrowPO borrow = dao.getBorrow(4, 104);
        if (borrow != null) {
            borrow.setReturnDate(20231220); // 更新归还日期
            dao.updateBorrow(borrow);

            System.out.println();
            System.out.println("== 更新借阅记录后 ==");
            testQueryRecords();
        } else {
            System.out.println("未找到 Sid=4 和 Bid=104 的借阅记录用于更新。");
        }
    }

    private void testDeleteRecord() {
        boolean deleted = dao.deleteBorrow(4, 104);
        if (deleted) {
            System.out.println();
            System.out.println("== 删除借阅记录后 ==");
            testQueryRecords();
        } else {
            System.out.println("未找到 Sid=4 和 Bid=104 的借阅记录用于删除。");
        }
    }

    public static void main(String[] args) {
        BorrowDAOTester daoTester = new BorrowDAOTester();
        daoTester.dropTable();
        daoTester.createTable();
        daoTester.insertInitRecords();

        // 测试插入仅包含借书日期的记录
        daoTester.testInsertBorrowOnlyRecord();

        // 测试还书操作，更新还书日期
        daoTester.testReturnBook();

        daoTester.testInsertRecord();
        daoTester.testUpdateRecord();
        daoTester.testDeleteRecord();

        daoTester.closeConnection();
    }
}
