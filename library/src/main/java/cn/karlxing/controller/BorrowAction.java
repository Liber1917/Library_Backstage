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
                if (borrowDAO.existsBorrow(Integer.parseInt(sid), Integer.parseInt(bid))) {
                    // 如果记录存在，设置提示信息并转发到显示页面
                    request.setAttribute("errorMessage", "ID为" + bid + "的书已被借出");
                } else {
                    boolean result = borrowDAO.addBorrow(borrow);
                    if (result) {
                        request.setAttribute("successMessage", "借书记录添加成功");
                    } else {
                        request.setAttribute("errorMessage", "添加借书记录失败");
                    }
                }
            } else if ("update".equals(operation)) {
                boolean result = borrowDAO.updateBorrow(borrow);
                if (result) {
                    request.setAttribute("successMessage", "借书记录更新成功");
                } else {
                    request.setAttribute("errorMessage", "更新借书记录失败");
                }
            } else if ("delete".equals(operation)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean result = borrowDAO.deleteBorrow(id);
                if (result) {
                    request.setAttribute("successMessage", "借书记录删除成功");
                } else {
                    request.setAttribute("errorMessage", "删除借书记录失败");
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "数据库操作失败: " + e.getMessage());
            e.printStackTrace(); // 打印堆栈跟踪以便于调试
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 转发到BRlog.jsp页面
        request.getRequestDispatcher("/BRlog.jsp").forward(request, response);
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