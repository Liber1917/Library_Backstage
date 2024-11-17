<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    DatabaseManager dbManager = null;
    BorrowDAO borrowDAO = null;
    List<BorrowPO> borrows = null;
    String errorMessage = null;
    try {
        dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
        Connection connection = dbManager.getConnection();
        if (connection == null) {
            throw new SQLException("无法建立数据库连接");
        }
        borrowDAO = new BorrowDAO(connection);
        borrows = borrowDAO.getAllBorrows();
        if (borrows == null || borrows.isEmpty()) {
            errorMessage = "没有找到借还记录。";
        }
    } catch (Exception e) {
        errorMessage = "数据库连接失败或查询出错：" + e.getMessage();
    } finally {
        if (dbManager != null) {
            dbManager.closeConnection();
        }
    }
%>

<html>
<head>
    <title>借还记录</title>
</head>
<body>
<h2>图书馆后台</h2>
<h2>借还记录</h2>

<% if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } else if (borrows != null) { %>
<table>
    <tr>
        <th>ID</th>
        <th>学生ID</th>
        <th>书籍ID</th>
        <th>借书日期</th>
        <th>还书日期</th>
        <th>操作</th>
    </tr>
    <% for (BorrowPO borrow : borrows) { %>
    <tr>
        <td><%= borrow.getId() %></td>
        <td><%= borrow.getStudentID() %></td>
        <td><%= borrow.getBookID() %></td>
        <td><%= borrow.getBorrowDate() %></td>
        <td><%= borrow.getReturnDate() == -1 ? "未还" : borrow.getReturnDate() %></td>
        <td>
            <button onclick="location.href='editLog.jsp?id=<%= borrow.getId() %>'">编辑</button>
            <form action="BorrowAction" method="post" style="display:inline;">
                <input type="hidden" name="operation" value="delete">
                <input type="hidden" name="id" value="<%= borrow.getId() %>">
                <button type="submit" onclick="return confirm('确定删除吗？')">删除</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } %>

<button onclick="location.href='addLog.jsp'">添加记录</button>

</body>
</html>