<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String sid = request.getParameter("sid");
    StudentPO student = null;
    if (sid != null) {
        DatabaseManager dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
        Connection connection = dbManager.getConnection();
        if (connection == null) {
            throw new SQLException("无法建立数据库连接");
        }
        StudentDAO studentDAO = new StudentDAO(connection);
        student = studentDAO.queryStudentByKey(Integer.parseInt(sid));
        dbManager.closeConnection();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>编辑学生</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑学生</h2>
<form action="StudentAction" method="post">
    <input type="hidden" name="operation" value="update">
    <input type="hidden" name="Sid" value="<%= student != null ? student.getID() : "" %>">
    姓名：<input type="text" name="Sname" value="<%= student != null ? student.getName() : "" %>" required><br>
    系别：<input type="text" name="Sdept" value="<%= student != null ? student.getDept() : "" %>" required><br>
    专业：<input type="text" name="Smajor" value="<%= student != null ? student.getMajor() : "" %>" required><br>
    已借图书数量：<input type="number" name="Sborrowed" value="<%= student != null ? student.getBorrowed() : "" %>" required><br>
    <input type="submit" value="更新学生" onclick="return confirm('确定要更新吗？')">
</form>
</body>
</html>