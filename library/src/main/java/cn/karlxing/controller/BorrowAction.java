package cn.karlxing.controller;

import cn.karlxing.JavaBean.BorrowDAO;
import cn.karlxing.JavaBean.BorrowPO;
import cn.karlxing.JavaBean.StudentDAO;
import cn.karlxing.JavaBean.BookDAO;
import cn.karlxing.JavaBean.StudentPO;
import cn.karlxing.JavaBean.BookPO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/BorrowAction")
public class BorrowAction extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        StudentDAO studentDAO = new StudentDAO(cn);
        BookDAO bookDAO = new BookDAO(cn);
        BorrowDAO borrowDAO = new BorrowDAO(cn);

        List<StudentPO> students = studentDAO.queryStudents();
        List<BookPO> books = bookDAO.queryBooks();
        List<BorrowPO> borrows = borrowDAO.getAllBorrows();

        request.setAttribute("students", students);
        request.setAttribute("books", books);
        request.setAttribute("borrows", borrows);

        request.getRequestDispatcher("/BRlog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        String operation = request.getParameter("operation");
        BorrowDAO borrowDAO = new BorrowDAO(cn);

        if ("insert".equals(operation)) {
            BorrowPO borrow = new BorrowPO();
            int studentID = Integer.parseInt(request.getParameter("studentID"));
            int bookID = Integer.parseInt(request.getParameter("bookID"));
            int borrowDate = Integer.parseInt(request.getParameter("borrowDate"));
            int returnDate = request.getParameter("returnDate") == null || request.getParameter("returnDate").isEmpty() ? -1 : Integer.parseInt(request.getParameter("returnDate"));

            borrow.setStudentID(studentID);
            borrow.setBookID(bookID);
            borrow.setBorrowDate(borrowDate);
            borrow.setReturnDate(returnDate);

            boolean success = borrowDAO.addBorrow(borrow);
            if (success) {
                response.sendRedirect("BRlog.jsp");
            } else {
                response.sendRedirect("addLog.jsp?error=UnableToAdd");
            }
        }
    }
}