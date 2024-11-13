<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String bid = request.getParameter("bid");
  BorrowPO borrow = null;
  if (bid != null) {
    DatabaseManager dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
    Connection connection = dbManager.getConnection();
    if (connection == null) {
      throw new SQLException("无法建立数据库连接");
    }
    BorrowDAO borrowDAO = new BorrowDAO(connection);
    borrow = borrowDAO.getBorrow(Integer.parseInt(bid));
    dbManager.closeConnection();
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>编辑借还记录</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑借还记录</h2>
<form action="BorrowAction" method="post">
  <input type="hidden" name="operation" value="update">
  <input type="hidden" name="Bid" value="<%= borrow != null ? borrow.getId() : "" %>">
  学生ID：<input type="text" name="Sid" value="<%= borrow != null ? borrow.getStudentID() : "" %>" required><br>
  书籍ID：<input type="text" name="Bid" value="<%= borrow != null ? borrow.getBookID() : "" %>" required><br>
  借书日期：<input type="date" name="borrowDate" value="<%= borrow != null ? borrow.getBorrowDate() : "" %>" required><br>
  还书日期：<input type="date" name="returnDate" value="<%= borrow != null ? borrow.getReturnDate() : "" %>"><br>
  <input type="submit" value="更新记录" onclick="return confirm('确定要更新吗？')">
</form>
</body>
</html>