<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String bid = request.getParameter("bid");
    BookPO book = null;
    if (bid != null) {
        DatabaseManager dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
        Connection connection = dbManager.getConnection();
        BookDAO bookDAO = new BookDAO(connection);
        book = bookDAO.queryBookByKey(Integer.parseInt(bid));
        dbManager.closeConnection();
    }
%>
<html>
<head>
    <title>编辑图书</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑图书</h2>
<form action="BookAction" method="post">
    <input type="hidden" name="operation" value="update">
    ID：<input type="text" name="Bid" value="<%= book != null ? book.getId() : "" %>"><br>
    书名：<input type="text" name="Btitle" value="<%= book != null ? book.getTitle() : "" %>"><br>
    作者：<input type="text" name="Bauthor" value="<%= book != null ? book.getAuthor() : "" %>"><br>
    版本：<input type="text" name="Bversion" value="<%= book != null ? book.getVersion() : "" %>"><br>
    <input type="submit" value="更新图书" onclick="return confirm('确定要更新吗？')">
</form>
</body>
</html>