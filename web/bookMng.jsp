<%@ page import="java.util.*, cn.karlxing.JavaBean.BookDAO, cn.karlxing.JavaBean.BookPO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Book Management</title>
</head>
<body>

<h2>Book Management</h2>

<%
    BookDAO bookDAO = new BookDAO();
    String action = request.getParameter("action");
    String message = "";

    if ("add".equals(action)) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String version = request.getParameter("version");

        BookPO newBook = new BookPO();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setVersion(version);
        bookDAO.addBook(newBook);
        message = "Book added successfully!";
    } else if ("delete".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        BookPO bookToDelete = bookDAO.queryBookByKey(id);
        if (bookToDelete != null) {
            bookDAO.deleteBook(bookToDelete);
            message = "Book deleted successfully!";
        } else {
            message = "Book not found.";
        }
    } else if ("update".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String version = request.getParameter("version");

        BookPO bookToUpdate = bookDAO.queryBookByKey(id);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(title);
            bookToUpdate.setAuthor(author);
            bookToUpdate.setVersion(version);
            bookDAO.updateBook(bookToUpdate);
            message = "Book updated successfully!";
        } else {
            message = "Book not found.";
        }
    }

    List<BookPO> books = bookDAO.queryBooks();
%>

<%-- Display message if any --%>
<% if (!message.isEmpty()) { %>
<p><b><%= message %></b></p>
<% } %>

<!-- Book List Table -->
<h3>Book List</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Version</th>
        <th>Actions</th>
    </tr>
    <% for (BookPO book : books) { %>
    <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getVersion() %></td>
        <td>
            <!-- Delete Button -->
            <form action="bookMng.jsp" method="post" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= book.getId() %>">
                <input type="submit" value="Delete">
            </form>

            <!-- Update Button -->
            <form action="bookMng.jsp" method="post" style="display:inline;">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= book.getId() %>">
                Title: <input type="text" name="title" value="<%= book.getTitle() %>">
                Author: <input type="text" name="author" value="<%= book.getAuthor() %>">
                Version: <input type="text" name="version" value="<%= book.getVersion() %>">
                <input type="submit" value="Update">
            </form>
        </td>
    </tr>
    <% } %>
</table>

<!-- Add Book Form -->
<h3>Add a New Book</h3>
<form action="bookMng.jsp" method="post">
    <input type="hidden" name="action" value="add">
    Title: <input type="text" name="title" required><br>
    Author: <input type="text" name="author" required><br>
    Version: <input type="text" name="version" required><br>
    <input type="submit" value="Add Book">
</form>

</body>
</html>
