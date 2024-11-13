package cn.karlxing.controller;

import cn.karlxing.JavaBean.DatabaseManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DatabaseServlet extends HttpServlet {

    private DatabaseManager dbManager;

    @Override
    public void init() throws ServletException {
        // 从 web.xml 配置中获取初始化参数
        String dbUrl = getServletConfig().getInitParameter("dbUrl");
        String driverName = getServletConfig().getInitParameter("driverName");
        String userName = getServletConfig().getInitParameter("userName");
        String passWord = getServletConfig().getInitParameter("passWord");

        // 初始化 DatabaseManager
        dbManager = new DatabaseManager(driverName, dbUrl, userName, passWord);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 示例：可以返回数据库连接状态
        if (dbManager.getConnection() != null) {
            response.getWriter().println("数据库连接成功！");
        } else {
            response.getWriter().println("数据库连接失败！");
        }
    }

    @Override
    public void destroy() {
        // 销毁时关闭数据库连接
        dbManager.closeConnection();
    }
}
