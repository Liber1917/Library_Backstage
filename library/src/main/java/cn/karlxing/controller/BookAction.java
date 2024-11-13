package cn.karlxing.controller;

import cn.karlxing.JavaBean.BookDAO;
import cn.karlxing.JavaBean.BookPO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

public class BookAction implements IAction {

    public String execute(HttpServletRequest request, Connection cn) {
        String operation = request.getParameter("operation");
        BookDAO bookDAO = new BookDAO(cn);  // 使用数据库连接初始化 BookDAO

        // 获取并处理书籍数据
        if (operation != null && (operation.equals("insert") || operation.equals("update") || operation.equals("delete"))) {
            BookPO book = new BookPO();

            // 获取书籍输入的参数
            String id = request.getParameter("Bid");
            if (id != null && !id.isEmpty()) {
                book.setId(Integer.parseInt(id));
            }
            String title = request.getParameter("Btitle");
            book.setTitle(title);
            String author = request.getParameter("Bauthor");
            book.setAuthor(author);
            String version = request.getParameter("Bversion");
            book.setVersion(version);

            // 根据操作类型进行相应的数据库操作
            if (operation.equals("insert")) {
                bookDAO.addBook(book);
            } else if (operation.equals("update")) {
                bookDAO.updateBook(book);
            } else if (operation.equals("delete")) {
                bookDAO.deleteBook(book);
            }
        }

        // 查询所有书籍数据
        List<BookPO> books = bookDAO.queryBooks();
        request.setAttribute("bookList", books);  // 将书籍数据传递到 JSP 页面

        return "/BookList.jsp";  // 返回处理后的 JSP 页面
    }
}
