<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    DatabaseManager dbManager = null;
    BookDAO bookDAO = null;
    List<BookPO> books = null;
    String errorMessage = null;
    try {
        dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
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
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .error-message {
            color: red;
        }
        .success-message {
            color: green;
        }
    </style>
</head>
<body>
<h2>图书馆后台</h2>
<h2>图书管理</h2>

<% if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } else if (books != null) { %>
<table>
    <tr>
        <th>ID</th>
        <th>书名</th>
        <th>作者</th>
        <th>版本</th>
        <th>操作</th>
    </tr>
    <% for (BookPO book : books) { %>
    <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getVersion() %></td>
        <td>
            <button onclick="location.href='editBook.jsp?bid=<%= book.getId() %>'">编辑</button>
            <form action="BookAction" method="post" style="display:inline;">
                <input type="hidden" name="operation" value="delete">
                <input type="hidden" name="Bid" value="<%= book.getId() %>">
                <button type="submit" onclick="return confirm('确定删除吗？')">删除</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } %>

<button onclick="location.href='addBook.jsp'">添加图书</button>
<button onclick="location.href='portal.jsp'">回到门户</button>
</body>
</html>