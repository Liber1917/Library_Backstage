package cn.karlxing.controller;

import cn.karlxing.JavaBean.StudentDAO;
import cn.karlxing.JavaBean.StudentPO;
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

@WebServlet("/StudentAction")
public class StudentAction extends HttpServlet {

    // 处理GET请求，通常用于查询学生列表
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        StudentDAO studentDAO = new StudentDAO(cn);
        List<StudentPO> students = studentDAO.queryStudents();
        request.setAttribute("studentList", students); // 将学生数据传递到JSP页面

        // 转发请求到stuMng.jsp页面
        request.getRequestDispatcher("/stuMng.jsp").forward(request, response);
    }

    // 处理POST请求，通常用于添加、更新或删除学生
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection cn = getDatabaseConnection();
        if (cn == null) {
            throw new ServletException("数据库连接未建立");
        }

        StudentDAO studentDAO = new StudentDAO(cn);
        String operation = request.getParameter("operation");

        StudentPO student = new StudentPO();
        String id = request.getParameter("Sid");
        if (id != null && !id.isEmpty()) {
            student.setID(Integer.parseInt(id));
        }
        student.setName(request.getParameter("Sname"));
        student.setDept(request.getParameter("Sdept"));
        student.setMajor(request.getParameter("Smajor"));
        String borrowed = request.getParameter("Sborrowed");
        if (borrowed != null && !borrowed.isEmpty()) {
            student.setBorrowed(Integer.parseInt(borrowed));
        }

        try {
            if ("insert".equals(operation)) {
                studentDAO.addStudent(student);
            } else if ("update".equals(operation)) {
                studentDAO.updateStudent(student);
            } else if ("delete".equals(operation)) {
                studentDAO.deleteStudent(student);
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

        // 重定向到stuMng.jsp页面
        response.sendRedirect("stuMng.jsp");
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