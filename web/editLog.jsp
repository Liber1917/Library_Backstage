<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String id = request.getParameter("id"); // 获取借还记录的ID
  BorrowPO borrow = null;
  if (id != null) {
    DatabaseManager dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
    Connection connection = dbManager.getConnection();
    BorrowDAO borrowDAO = new BorrowDAO(connection);
    borrow = borrowDAO.getBorrow(Integer.parseInt(id)); // 根据ID查询借还记录
    dbManager.closeConnection();
  }

  // 调试输出
  if (borrow == null) {
    out.println("未找到借还记录，ID: " + id);
  } else {
    out.println("找到借还记录: " + borrow.getId() + ", 学生ID: " + borrow.getStudentID() + ", 书籍ID: " + borrow.getBookID());
  }
%>
<html>
<head>
  <title>编辑借还记录</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑借还记录</h2>
<form action="BorrowAction" method="post">
  <input type="hidden" name="operation" value="update">
  <input type="hidden" name="id" value="<%= borrow != null ? borrow.getId() : "" %>"> <!-- 隐藏字段，存储借还记录的ID -->

  学生ID：<input type="text" name="Sid" value="<%= borrow != null ? borrow.getStudentID() : "" %>" required><br>

  书籍ID：<input type="text" name="Bid" value="<%= borrow != null ? borrow.getBookID() : "" %>" required><br>

  借书日期：<input type="text" name="borrowDate" value="<%= borrow != null ? String.format("%06d", borrow.getBorrowDate()) : "" %>" required placeholder="格式：yyMMdd"><br>

  还书日期：<input type="text" name="returnDate" value="<%= borrow != null && borrow.getReturnDate() != 0 ? String.format("%06d", borrow.getReturnDate()) : "" %>" placeholder="格式：yyMMdd 或者留空表示未还"><br>

  <input type="submit" value="更新记录" onclick="return confirm('确定要更新吗？')">
</form>
</body>
</html>