<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>

<html>
<head>
    <title>借还登记</title>
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
<h1>图书馆后台</h1>
<h2>借还登记</h2>

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

<% if (errorMessage != null) { %>
<p class="error-message"><%= errorMessage %></p>
<% } else if (borrows != null) { %>
<table>
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
        <td><%= String.format("%06d", borrow.getBorrowDate()) %></td>
        <td><%= borrow.getReturnDate() != 0 ? String.format("%06d", borrow.getReturnDate()) : "未还" %></td>
        <td>
            <a href="editBorrow.jsp?sid=<%= borrow.getStudentID() %>&bid=<%= borrow.getBookID() %>">编辑</a>
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

<!-- 添加借还记录的表单 -->
<form action="BorrowAction" method="post">
    <input type="hidden" name="operation" value="insert">
    学生ID：<input type="text" name="Sid" required><br>
    书籍ID：<input type="text" name="Bid" required><br>
    借书日期：<input type="text" name="borrowDate" required placeholder="格式：yyMMdd"><br>
    还书日期：<input type="text" name="returnDate" placeholder="格式：yyMMdd 或者留空表示未还"><br>
    <input type="submit" value="添加记录">
</form>

</body>
</html>