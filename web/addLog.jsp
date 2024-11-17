<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  DatabaseManager dbManager = null;
  StudentDAO studentDAO = null;
  BookDAO bookDAO = null;
  BorrowDAO borrowDAO = null;
  List<StudentPO> students = null;
  List<BookPO> books = null;
  List<BorrowPO> borrows = null;
  try {
    dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
    Connection connection = dbManager.getConnection();
    if (connection == null) {
      throw new SQLException("无法建立数据库连接");
    }
    studentDAO = new StudentDAO(connection);
    bookDAO = new BookDAO(connection);
    borrowDAO = new BorrowDAO(connection);
    students = studentDAO.queryStudents();
    books = bookDAO.queryBooks();
    borrows = borrowDAO.getAllBorrows();
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if (dbManager != null) {
      dbManager.closeConnection();
    }
  }
%>

<html>
<head>
  <title>添加借还记录</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>添加借还记录</h2>
<form action="BorrowAction" method="post">
  <input type="hidden" name="operation" value="insert">
  学生：<select name="studentID">
  <% for (StudentPO student : students) { %>
  <option value="<%= student.getID() %>"><%= student.getName() %></option>
  <% } %>
</select><br>
  书籍：<select name="bookID">
  <% for (BookPO book : books) { %>
  <%
    boolean isBorrowed = false;
    for (BorrowPO borrow : borrows) {
      if (borrow.getBookID() == book.getId() && borrow.getReturnDate() == -1) {
        isBorrowed = true;
        break;
      }
    }
  %>
  <option value="<%= book.getId() %>" <%= isBorrowed ? "style='color: grey; background-color: #f1f1f1; pointer-events: none;'" : "" %>><%= book.getTitle() %></option>
  <% } %>
</select><br>
  借书日期：<input type="text" name="borrowDate" required><br>
  还书日期：<input type="text" name="returnDate" value="-1" required><br>
  <input type="submit" value="添加记录">
</form>
<%
  String error = request.getParameter("error");
  if ("UnableToAdd".equals(error)) {
    out.println("<p style='color: red'>该书已经被该学生借阅。</p>");
  } else if ("InvalidData".equals(error)) {
    out.println("<p style='color: red'>输入数据无效。</p>");
  } else if ("DatabaseError".equals(error)) {
    out.println("<p style='color: red'>数据库操作失败。</p>");
  }
%>
</body>
</html>