<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String id = request.getParameter("id");
  if (id == null || id.isEmpty()) {
    out.println("<p>No ID parameter provided.</p>");
    return;
  }
  BorrowPO borrow = null;
  DatabaseManager dbManager = null;
  StudentDAO studentDAO = null;
  BookDAO bookDAO = null;
  List<StudentPO> students = null;
  List<BookPO> books = null;
  try {
    dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
    Connection connection = dbManager.getConnection();
    if (connection == null) {
      throw new SQLException("无法建立数据库连接");
    }
    studentDAO = new StudentDAO(connection);
    bookDAO = new BookDAO(connection);
    students = studentDAO.queryStudents();
    books = bookDAO.queryBooks();
    BorrowDAO borrowDAO = new BorrowDAO(connection);
    borrow = borrowDAO.getBorrow(Integer.parseInt(id));
  } catch (Exception e) {
    e.printStackTrace();
    out.println("<p>发生错误: " + e.getMessage() + "</p>");
  } finally {
    if (dbManager != null) {
      dbManager.closeConnection();
    }
  }
%>

<html>
<head>
  <title>编辑借还记录</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑借还记录</h2>
<%
  if (borrow == null) {
    out.println("<p>无法找到借还记录。请检查ID是否正确。</p>");
  } else {
%>
<form action="BorrowAction" method="post">
  <input type="hidden" name="operation" value="update">
  <input type="hidden" name="id" value="<%= borrow.getId() %>">
  学生：<select name="studentID">
  <% for (StudentPO student : students) { %>
  <option value="<%= student.getID() %>" <%= (student.getID() == borrow.getStudentID()) ? "selected" : "" %>><%= student.getName() %></option>
  <% } %>
</select><br>
  书籍：<select name="bookID">
  <% for (BookPO book : books) { %>
  <option value="<%= book.getId() %>" <%= (book.getId() == borrow.getBookID()) ? "selected" : "" %>><%= book.getTitle() %></option>
  <% } %>
</select><br>
  借书日期：<input type="text" name="borrowDate" value="<%= borrow.getBorrowDate() %>" required><br>
  还书日期：<input type="text" name="returnDate" value="<%= borrow.getReturnDate() == -1 ? "" : String.valueOf(borrow.getReturnDate()) %>" required><br>
  <input type="submit" value="更新记录">
</form>
<%
  }
%>
</body>
</html>