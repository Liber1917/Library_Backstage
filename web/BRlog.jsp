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
    } catch (Exception e) {
        errorMessage = "数据库连接失败或查询出错：" + e.getMessage();
    } finally {
        if (dbManager != null) {
            dbManager.closeConnection();
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>借还登记</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>借还登记</h2>

<% if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } else if (borrows != null) { %>
<table border="1">
    <tr>
        <th>学生ID</th>
        <th>书籍ID</th>
        <th>借书日期</th>
        <th>还书日期</th>
        <th>操作</th>
    </tr>
    <% for (BorrowPO borrow : borrows) { %>
    <tr>
        <td><%= borrow.getStudentID() %></td>
        <td><%= borrow.getBookID() %></td>
        <td><%= borrow.getBorrowDate() %></td>
        <td><%= borrow.getReturnDate() %></td>
        <td>
            <a href="editBorrow.jsp?sid=<%= borrow.getStudentID() %>&bid=<%= borrow.getBookID() %>">编辑</a>
            <form action="BorrowAction" method="post" style="display:inline;">
                <input type="hidden" name="operation" value="delete">
                <input type="hidden" name="Sid" value="<%= borrow.getStudentID() %>">
                <input type="hidden" name="Bid" value="<%= borrow.getBookID() %>">
                <button type="submit" onclick="return confirm('确定删除吗？')">删除</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } %>

<form action="BorrowAction" method="post">
    <input type="hidden" name="operation" value="insert">
    学生ID：<input type="text" name="Sid" required><br>
    书籍ID：<input type="text" name="Bid" required><br>
    借书日期：<input type="date" name="borrowDate" required><br>
    还书日期：<input type="date" name="returnDate"><br>
    <input type="submit" value="添加记录">
</form>

</body>
</html>