<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    DatabaseManager dbManager = null;
    BookDAO bookDAO = null;
    List<BookPO> books = null;
    String errorMessage = null;
    try {
        dbManager = new DatabaseManager("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/Library/mngsys", "root", "");
        Connection connection = dbManager.getConnection();
        if (connection == null) {
            throw new SQLException("无法建立数据库连接");
        }
        bookDAO = new BookDAO(connection);
        books = bookDAO.queryBooks();
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
    <title>图书管理</title>
</head>
<body>
<h1>图书列表</h1>
<% if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } else if (books != null) { %>
<table>
    <tr>
        <th>ID</th>
        <th>书名</th>
        <th>作者</th>
        <th>版本</th>
    </tr>
    <% for (BookPO book : books) { %>
    <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getVersion() %></td>
    </tr>
    <% } %>
</table>
<% } %>
</body>
</html>