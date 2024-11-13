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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库连接未建立");
            return;
        }

        BorrowDAO borrowDAO = new BorrowDAO(cn);
        String operation = request.getParameter("operation");

        try {
            if ("insert".equals(operation)) {
                int sid = Integer.parseInt(request.getParameter("Sid"));
                int bid = Integer.parseInt(request.getParameter("Bid"));
                int borrowDate = Integer.parseInt(request.getParameter("borrowDate"));
                String returnDateStr = request.getParameter("returnDate");
                int returnDate = returnDateStr != null && !returnDateStr.equals("未还") ? Integer.parseInt(returnDateStr) : 0;
                BorrowPO borrow = new BorrowPO(); // 使用无参数构造器
                borrow.setStudentID(sid);
                borrow.setBookID(bid);
                borrow.setBorrowDate(borrowDate);
                borrow.setReturnDate(returnDate);
                boolean result = borrowDAO.addBorrow(borrow);
                if (!result) {
                    throw new Exception("添加借书记录失败");
                }
            } else if ("update".equals(operation)) {
                int sid = Integer.parseInt(request.getParameter("Sid"));
                int bid = Integer.parseInt(request.getParameter("Bid"));
                int borrowDate = Integer.parseInt(request.getParameter("borrowDate"));
                String returnDateStr = request.getParameter("returnDate");
                int returnDate = returnDateStr != null && !returnDateStr.equals("未还") ? Integer.parseInt(returnDateStr) : 0;
                BorrowPO borrow = new BorrowPO(); // 使用无参数构造器
                borrow.setStudentID(sid);
                borrow.setBookID(bid);
                borrow.setBorrowDate(borrowDate);
                borrow.setReturnDate(returnDate);
                boolean result = borrowDAO.updateBorrow(borrow);
                if (!result) {
                    throw new Exception("更新借书记录失败");
                }
            } else if ("delete".equals(operation)) {
                int sid = Integer.parseInt(request.getParameter("Sid"));
                int bid = Integer.parseInt(request.getParameter("Bid"));
                boolean result = borrowDAO.deleteBorrow(sid, bid);
                if (!result) {
                    throw new Exception("删除借书记录失败");
                }
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 确保在没有提交响应的情况下重定向
        response.sendRedirect("BRlog.jsp");
    }
}