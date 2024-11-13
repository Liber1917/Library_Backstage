<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="cn.karlxing.JavaBean.BorrowPO" %>
<%@ page import="java.util.List" %>

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

<% if (request.getAttribute("errorMessage") != null) { %>
<p class="error-message"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<% if (request.getAttribute("successMessage") != null) { %>
<p class="success-message"><%= request.getAttribute("successMessage") %></p>
<% } %>

<!-- 显示借还记录的表格 -->
<table>
    <tr>
        <th>学生ID</th>
        <th>书籍ID</th>
        <th>借书日期</th>
        <th>还书日期</th>
        <th>操作</th>
    </tr>
    <%
        List<BorrowPO> borrows = (List<BorrowPO>) request.getAttribute("borrowList");
        if (borrows != null) {
            for (BorrowPO borrow : borrows) {
    %>
    <tr>
        <td><%= borrow.getStudentID() %></td>
        <td><%= borrow.getBookID() %></td>
        <td><%= String.format("%06d", borrow.getBorrowDate()) %></td>
        <td><%= borrow.getReturnDate() != 0 ? String.format("%06d", borrow.getReturnDate()) : "未还" %></td>
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
    <%
            }
        }
    %>
</table>

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