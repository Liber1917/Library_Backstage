package cn.karlxing.controller;

import cn.karlxing.JavaBean.StudentDAO;
import cn.karlxing.JavaBean.StudentPO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

public class StudentAction implements IAction {

    public String execute(HttpServletRequest request, Connection cn) {
        String operation = request.getParameter("operation");
        StudentDAO studentDAO = new StudentDAO(cn);  // 使用数据库连接初始化 StudentDAO

        // 获取并处理学生数据
        if (operation != null && (operation.equals("insert") || operation.equals("update") || operation.equals("delete"))) {
            StudentPO student = new StudentPO();

            // 获取学生输入的参数
            String id = request.getParameter("Sid");
            if (id != null && !id.isEmpty()) {
                student.setID(Integer.parseInt(id));
            }
            String name = request.getParameter("Sname");
            student.setName(name);
            String dept = request.getParameter("Sdept");
            student.setDept(dept);
            String major = request.getParameter("Smajor");
            student.setMajor(major);
            String borrowed = request.getParameter("Sborrowed");
            if (borrowed != null && !borrowed.isEmpty()) {
                student.setBorrowed(Integer.parseInt(borrowed));
            }

            // 根据操作类型进行相应的数据库操作
            if (operation.equals("insert")) {
                studentDAO.addStudent(student);
            } else if (operation.equals("update")) {
                studentDAO.updateStudent(student);
            } else if (operation.equals("delete")) {
                studentDAO.deleteStudent(student);
            }
        }

        // 查询学生数据
        List<StudentPO> students = studentDAO.queryStudents();
        request.setAttribute("studentList", students);  // 将学生数据传递到 JSP 页面

        return "/StudentList.jsp";  // 返回处理后的 JSP 页面
    }
}
