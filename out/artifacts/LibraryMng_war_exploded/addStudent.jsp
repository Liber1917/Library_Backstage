<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>添加学生</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>添加学生</h2>
<form action="StudentAction" method="post">
  ID：<input type="text" name="Sid" required><br>
  姓名：<input type="text" name="Sname" required><br>
  系别：<input type="text" name="Sdept" required><br>
  专业：<input type="text" name="Smajor" required><br>
  已借图书数量：<input type="number" name="Sborrowed" required><br>
  <input type="hidden" name="operation" value="insert"> <!-- 隐藏字段指定操作类型 -->
  <input type="submit" value="添加学生">
</form>
</body>
</html>