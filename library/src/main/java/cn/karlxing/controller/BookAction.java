package cn.karlxing.controller;

import cn.karlxing.JavaBean.BookDAO;
import cn.karlxing.JavaBean.BookPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List; // 确保导入了List接口
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 使用@WebServlet注解映射URL
@WebServlet("/BookAction")
public class BookAction extends HttpServlet {

    // 处理GET请求，通常用于查询书籍列表
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取数据库连接
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        BookDAO bookDAO = new BookDAO(cn);
        List<BookPO> books = bookDAO.queryBooks(); // 使用List接口
        request.setAttribute("bookList", books); // 将书籍数据传递到JSP页面

        // 转发请求到bookMng.jsp页面
        request.getRequestDispatcher("/bookMng.jsp").forward(request, response);
    }

    // 处理POST请求，通常用于添加、更新或删除书籍
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取数据库连接
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        BookDAO bookDAO = new BookDAO(cn);
        String operation = request.getParameter("operation");

        BookPO book = new BookPO();
        String id = request.getParameter("Bid");
        if (id != null && !id.isEmpty()) {
            book.setId(Integer.parseInt(id));
        }
        book.setTitle(request.getParameter("Btitle"));
        book.setAuthor(request.getParameter("Bauthor"));
        book.setVersion(request.getParameter("Bversion"));

        try {
            if ("insert".equals(operation)) {
                bookDAO.addBook(book);
            } else if ("update".equals(operation)) {
                bookDAO.updateBook(book);
            } else if ("delete".equals(operation)) {
                bookDAO.deleteBook(book);
            }
        } catch (Exception e) {
            throw new ServletException("数据库操作失败", e);
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 重定向到bookMng.jsp页面
        response.sendRedirect("bookMng.jsp");
    }

    // 获取数据库连接的方法
    private Connection getDatabaseConnection() {
        String jdbcURL = "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}