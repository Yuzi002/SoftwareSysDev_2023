package com.Yuzi.web.servlet;

import com.Yuzi.web.dao.EmployeeDao;
import com.Yuzi.web.dao.impl.EmployeeDaoImpl;
import com.Yuzi.web.pojo.Employee;
import com.Yuzi.web.pojo.R;
import com.Yuzi.web.util.FormatStringAsDate;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(urlPatterns = {"/employeeList.action", "/addEmployee.action", "/checkCardId.action"})
public class EmployeeServlet extends HttpServlet {
  private EmployeeDao employeeDao = new EmployeeDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "employeeList.action" -> {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        String name = request.getParameter("name");
        int sex = Integer.parseInt(request.getParameter("sex") == null || request.getParameter("sex").isEmpty() ? "-1" : request.getParameter("sex"));
        int jobId = Integer.parseInt(request.getParameter("jobId") == null || request.getParameter("jobId").isEmpty() ? "-1" : request.getParameter("jobId"));
        int deptId = Integer.parseInt(request.getParameter("deptId") == null || request.getParameter("deptId").isEmpty() ? "-1" : request.getParameter("deptId"));
        String cardId = request.getParameter("cardId");
        String phone = request.getParameter("phone");
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSex(sex);
        employee.setJob_id(jobId);
        employee.setDept_id(deptId);
        employee.setCardId(cardId);
        employee.setPhone(phone);


        var list = employeeDao.getEmployeeList(employee);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", list);
        r.put("count", 0);
        r.put("code", 0);
        out.print(new Gson().toJson(r));
      }
      case "checkCardId.action"->{
        var id = request.getParameter("cardId");
        var res = employeeDao.selectCardId(id);
        if (res) {
          out.print(0);
        } else {
          out.print(1);
        }
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "addEmployee.action" -> {
        String name = request.getParameter("name");
        String cardId = request.getParameter("cardId");
        String sex = request.getParameter("sex");
        String jobId = request.getParameter("jobId");
        String education = request.getParameter("education");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String tel = request.getParameter("tel");
        String party = request.getParameter("party");
        String qqNum = request.getParameter("qqNum");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postCode");
        String birthday = request.getParameter("birthday");
        String race = request.getParameter("race");
        String speciality = request.getParameter("speciality");
        String hobby = request.getParameter("hobby");
        String remark = request.getParameter("remark");
        String deptId = request.getParameter("deptId");
        Employee employee = new Employee(name, cardId, address, phone, email, Integer.parseInt(sex), education, new Date(), Integer.parseInt(deptId), Integer.parseInt(jobId), postCode, qqNum, party, FormatStringAsDate.formart(birthday), race, 0, speciality, hobby, remark, tel);

        if (employeeDao.addEmployee(employee) > 0) {//添加成功返回1
//        print, write：当返回的值是页面标签的时候才用
          out.print(1);
        } else {//添加失败返回0
          out.print(0);
        }
      }
    }
  }
}
