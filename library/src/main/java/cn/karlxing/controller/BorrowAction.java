package cn.karlxing.controller;

import cn.karlxing.JavaBean.BorrowDAO;
import cn.karlxing.JavaBean.BorrowPO;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        BorrowDAO borrowDAO = new BorrowDAO(cn);
        String operation = request.getParameter("operation");

        BorrowPO borrow = new BorrowPO();
        String sid = request.getParameter("Sid");
        String bid = request.getParameter("Bid");
        String borrowDateStr = request.getParameter("borrowDate");
        String returnDateStr = request.getParameter("returnDate");
        int returnDate = returnDateStr != null && !returnDateStr.isEmpty() ? Integer.parseInt(returnDateStr) : 0;

        try {
            if (sid != null && !sid.isEmpty() && bid != null && !bid.isEmpty() && borrowDateStr != null && !borrowDateStr.isEmpty()) {
                borrow.setStudentID(Integer.parseInt(sid));
                borrow.setBookID(Integer.parseInt(bid));
                borrow.setBorrowDate(Integer.parseInt(borrowDateStr));
                borrow.setReturnDate(returnDate);
            } else {
                throw new ServletException("缺少必要的参数");
            }

            if ("insert".equals(operation)) {
                boolean result = borrowDAO.addBorrow(borrow);
                if (result) {
                    // 重新获取所有借还记录并生成新行的 HTML
                    List<BorrowPO> borrows = borrowDAO.getAllBorrows();
                    if (borrows != null && !borrows.isEmpty()) {
                        BorrowPO newBorrow = borrows.get(borrows.size() - 1); // 获取最新添加的记录
                        String newTableRow = generateTableRow(newBorrow);
                        response.getWriter().write(newTableRow);
                    } else {
                        response.getWriter().write("<p class='error-message'>添加失败，没有找到新记录</p>");
                    }
                } else {
                    response.getWriter().write("<p class='error-message'>添加失败</p>");
                }
            }
        } catch (Exception e) {
            response.getWriter().write("<p class='error-message'>" + e.getMessage() + "</p>");
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

    private String generateTableRow(BorrowPO borrow) {
        return "<tr>" +
                "<td>" + borrow.getStudentID() + "</td>" +
                "<td>" + borrow.getBookID() + "</td>" +
                "<td>" + String.format("%06d", borrow.getBorrowDate()) + "</td>" +
                "<td>" + (borrow.getReturnDate() != 0 ? String.format("%06d", borrow.getReturnDate()) : "未还") + "</td>" +
                "<td>" +
                "<button onclick='editLog(" + borrow.getId() + ")'>编辑</button>" +
                "<form action='BorrowAction' method='post' style='display:inline;'>" +
                "<input type='hidden' name='operation' value='delete'>" +
                "<input type='hidden' name='id' value='" + borrow.getId() + "'>" +
                "<button type='submit' onclick='return confirm(\"确定删除吗？\")'>删除</button>" +
                "</form>" +
                "</td>" +
                "</tr>";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理GET请求的逻辑（如果有需要）
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        BorrowDAO borrowDAO = new BorrowDAO(cn);
        List<BorrowPO> borrows = borrowDAO.getAllBorrows();
        request.setAttribute("borrowList", borrows);
        request.getRequestDispatcher("/BRlog.jsp").forward(request, response);
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