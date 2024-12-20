package cn.karlxing.controller;

import cn.karlxing.JavaBean.*;

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
            try {
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                int bookID = Integer.parseInt(request.getParameter("bookID"));
                int borrowDate = Integer.parseInt(request.getParameter("borrowDate"));
                int returnDate = request.getParameter("returnDate") == null || request.getParameter("returnDate").isEmpty() ? -1 : Integer.parseInt(request.getParameter("returnDate"));

                // 检查书籍是否已被借出且未还
                boolean isBorrowed = borrowDAO.isBookCurrentlyBorrowed(bookID);

                if (isBorrowed) {
                    response.sendRedirect("addLog.jsp?error=UnableToAdd");
                } else {
                    BorrowPO borrow = new BorrowPO();
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
            } catch (NumberFormatException e) {
                response.sendRedirect("addLog.jsp?error=InvalidData");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("addLog.jsp?error=DatabaseError");
            } finally {
                if (cn != null) {
                    try {
                        cn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if ("delete".equals(operation)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean success = borrowDAO.deleteBorrow(id);
                if (success) {
                    response.sendRedirect("BRlog.jsp");
                } else {
                    response.sendRedirect("BRlog.jsp?error=UnableToDelete");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("BRlog.jsp?error=InvalidData");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("BRlog.jsp?error=DatabaseError");
            } finally {
                if (cn != null) {
                    try {
                        cn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if ("update".equals(operation)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int studentID = Integer.parseInt(request.getParameter("studentID"));
                int bookID = Integer.parseInt(request.getParameter("bookID"));
                int borrowDate = Integer.parseInt(request.getParameter("borrowDate"));
                int returnDate = request.getParameter("returnDate") == null || request.getParameter("returnDate").isEmpty() ? -1 : Integer.parseInt(request.getParameter("returnDate"));

                BorrowPO borrow = new BorrowPO();
                borrow.setId(id);
                borrow.setStudentID(studentID);
                borrow.setBookID(bookID);
                borrow.setBorrowDate(borrowDate);
                borrow.setReturnDate(returnDate);

                boolean success = borrowDAO.updateBorrow(borrow);
                if (success) {
                    response.sendRedirect("BRlog.jsp");
                } else {
                    response.sendRedirect("editLog.jsp?id=" + id + "&error=UnableToUpdate");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("editLog.jsp?id=" + request.getParameter("id") + "&error=InvalidData");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("editLog.jsp?id=" + request.getParameter("id") + "&error=DatabaseError");
            } finally {
                if (cn != null) {
                    try {
                        cn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}