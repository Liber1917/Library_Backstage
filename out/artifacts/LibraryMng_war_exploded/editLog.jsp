<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>

<html>
<head>
  <title>编辑借还记录</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>编辑借还记录</h2>

<%
  // 从session中获取借还记录
  BorrowPO borrow = (BorrowPO) session.getAttribute("borrow");
  if (borrow == null) {
    // 如果session中没有借还记录，尝试从请求参数中获取ID并查询数据库
    String id = request.getParameter("id");
    if (id != null && !id.isEmpty()) {
      DatabaseManager dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
      Connection connection = dbManager.getConnection();
      if (connection != null) {
        BorrowDAO borrowDAO = new BorrowDAO(connection);
        borrow = borrowDAO.getBorrow(Integer.parseInt(id));
        session.setAttribute("borrow", borrow); // 将借还记录存储到session中
        dbManager.closeConnection();
      }
    }
  }

  if (borrow == null) {
%>
<p>未找到借还记录。</p>
<%
} else {
%>
<form action="BorrowAction" method="post">
  <input type="hidden" name="operation" value="update">
  <input type="hidden" name="id" value="<%= borrow.getId() %>">
  学生ID：<input type="text" name="Sid" value="<%= borrow.getStudentID() %>" required><br>
  书籍ID：<input type="text" name="Bid" value="<%= borrow.getBookID() %>" required><br>
  借书日期：<input type="text" name="borrowDate" value="<%= String.format("%06d", borrow.getBorrowDate()) %>" required placeholder="格式：yyMMdd"><br>
  还书日期：<input type="text" name="returnDate" value="<%= borrow.getReturnDate() != 0 ? String.format("%06d", borrow.getReturnDate()) : "" %>" placeholder="格式：yyMMdd 或者留空表示未还"><br>
  <input type="submit" value="更新记录" onclick="return confirm('确定要更新吗？')">
</form>
<%
  }
%>

</body>
</html>