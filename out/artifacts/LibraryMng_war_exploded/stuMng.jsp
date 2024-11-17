<%@ page import="java.util.*, cn.karlxing.JavaBean.*, java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  DatabaseManager dbManager = null;
  StudentDAO studentDAO = null;
  List<StudentPO> students = null;
  String errorMessage = null;
  try {
    dbManager = new DatabaseManager("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mngsys?useSSL=false&serverTimezone=UTC", "root", "");
    Connection connection = dbManager.getConnection();
    if (connection == null) {
      throw new SQLException("无法建立数据库连接");
    }
    studentDAO = new StudentDAO(connection);
    students = studentDAO.queryStudents();
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
  <title>学生管理</title>
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
<h2>学生管理</h2>

<% if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } else if (students != null) { %>
<table>
  <tr>
    <th>ID</th>
    <th>姓名</th>
    <th>系别</th>
    <th>专业</th>
    <th>已借图书数量</th>
    <th>操作</th>
  </tr>
  <% for (StudentPO student : students) { %>
  <tr>
    <td><%= student.getID() %></td>
    <td><%= student.getName() %></td>
    <td><%= student.getDept() %></td>
    <td><%= student.getMajor() %></td>
    <td><%= student.getBorrowed() %></td>
    <td>
      <button onclick="location.href='editStudent.jsp?sid=<%= student.getID() %>'">编辑</button>
      <form action="StudentAction" method="post" style="display:inline;">
        <input type="hidden" name="operation" value="delete">
        <input type="hidden" name="Sid" value="<%= student.getID() %>">
        <button type="submit" onclick="return confirm('确定删除吗？')">删除</button>
      </form>
    </td>
  </tr>
  <% } %>
</table>
<% } %>

<button onclick="location.href='addStudent.jsp'">添加学生</button>
<button onclick="location.href='portal.jsp'">回到门户</button>
</body>
</html>