package cn.karlxing.controller;

import java.io.IOException;

import cn.karlxing.JavaBean.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationControllerServlet extends HttpServlet {
	private DatabaseManager dbManager;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("gbk");
		String entityName = request.getParameter("entity");
		if (entityName != null) {
			IAction entityAction = getEntityAction(entityName);
			String actionUrl = entityAction.execute(request, dbManager.getConnection());
			RequestDispatcher dipather = getServletContext().getRequestDispatcher(actionUrl);
			dipather.forward(request, response);
		}
	}

	public void init() throws ServletException {
		String jdbcDriver = getServletConfig().getInitParameter("jdbcDriver");
		String dbUrl = getServletConfig().getInitParameter("dbURL");
		String dbUser = getServletConfig().getInitParameter("dbUser");
		String dbPassword = getServletConfig().getInitParameter("dbPassword");
		dbManager = new DatabaseManager(jdbcDriver, dbUrl, dbUser, dbPassword);
	}

	public void destroy() {
		dbManager.closeConnection();
	}

	private IAction getEntityAction(String entityName) {
		IAction entityAction = null;
		try {
			Class actionClass = Class.forName("controller." + entityName + "Action"); // 此处是反射器，利用类名来生成类的实例
			Object actionObj = actionClass.newInstance();
			entityAction = (IAction) actionObj;
		} catch (Exception e) {
			System.out.println("get " + entityName + " dao instance error:" + e);
		}
		return entityAction;

	}

}
