package cn.karlxing.controller;

import cn.karlxing.JavaBean.BookDAO;
import cn.karlxing.JavaBean.BookPO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// 使用@WebServlet注解映射URL
@WebServlet("/BookAction")
public class BookAction extends HttpServlet {

    // 处理GET请求，通常用于查询书籍列表
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从ServletContext中获取数据库连接，假设您已经在那里初始化了数据库连接
        Connection cn = (Connection) getServletContext().getAttribute("dbConnection");
        if (cn == null) {
            throw new IllegalStateException("数据库连接未建立");
        }

        BookDAO bookDAO = new BookDAO(cn);
        List<BookPO> books = bookDAO.queryBooks();
        request.setAttribute("bookList", books); // 将书籍数据传递到JSP页面

        // 转发请求到bookMng.jsp页面
        request.getRequestDispatcher("/BookMng.jsp").forward(request, response);
    }

    // 处理POST请求，通常用于添加、更新或删除书籍
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection cn = (Connection) getServletContext().getAttribute("dbConnection");
        if (cn == null) {
            throw new IllegalStateException("数据库连接未建立");
        }

        BookDAO bookDAO = new BookDAO(cn);
        String operation = request.getParameter("operation");

        BookPO book = new BookPO(); // 创建BookPO对象

        // 获取书籍输入的参数
        String id = request.getParameter("Bid");
        if (id != null && !id.isEmpty()) {
            book.setId(Integer.parseInt(id)); // 将字符串转换为整数并设置为book的ID
        }
        book.setTitle(request.getParameter("Btitle")); // 设置书名
        book.setAuthor(request.getParameter("Bauthor")); // 设置作者
        book.setVersion(request.getParameter("Bversion")); // 设置版本

        // 根据操作类型进行相应的数据库操作
        if ("insert".equals(operation)) {
            bookDAO.addBook(book);
        } else if ("update".equals(operation)) {
            bookDAO.updateBook(book);
        } else if ("delete".equals(operation)) {
            int bookId = Integer.parseInt(request.getParameter("Bid")); // 直接获取ID用于删除操作
            bookDAO.deleteBook(bookId); // 传递ID给删除方法
        }

        // 重定向到bookMng.jsp页面
        response.sendRedirect("BookMng.jsp");
    }
}