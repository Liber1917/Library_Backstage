<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加图书</title>
</head>
<body>
<h1>图书馆后台</h1>
<h2>添加图书</h2>
<form action="BookAction" method="post">
    <input type="hidden" name="operation" value="insert">
    书籍ID：<input type="text" name="Bid" required><br>
    书名：<input type="text" name="Btitle" required><br>
    作者：<input type="text" name="Bauthor" required><br>
    版本：<input type="text" name="Bversion" required><br>
    <input type="submit" value="添加图书">
</form>
</body>
</html>