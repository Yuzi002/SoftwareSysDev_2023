package com.group6.backend.servlet;

import com.google.gson.Gson;
import com.group6.backend.dao.EmployeeDao;
import com.group6.backend.dao.impl.EmployeeDaoImpl;
import com.group6.backend.pojo.Employee;
import com.group6.backend.pojo.R;
import com.group6.backend.util.FormatStringAsDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

@WebServlet(urlPatterns = {"/employeeList.action", "/addEmployee.action", "/checkCardId.action", "/delEmployee.action", "/delEmployees.action", "/updEmployee.action"})
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
        r.put("count", employeeDao.countEmployee());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
      }
      case "checkCardId.action" -> {
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
      case "updEmployee.action" -> {
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
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = new Employee(id, name, cardId, address, phone, email, Integer.parseInt(sex), education, new Date(),
          Integer.parseInt(deptId), Integer.parseInt(jobId), postCode, qqNum, party, FormatStringAsDate.formart(birthday),
          race, 0, speciality, hobby, remark, tel);

        if (employeeDao.updEmployee(employee) > 0) {
          out.print(1);//修改成功
        } else {
          out.print(0);
        }
      }
      case "delEmployee.action" -> {
        int id = Integer.parseInt(request.getParameter("id"));
        if (employeeDao.delEmployee(id) > 0) {
          out.print(1);//删除成功
        } else {
          out.print(0);
        }
      }
      case "delEmployees.action" -> {
        var ids = Arrays.stream(request.getParameter("ids").split(",")).mapToInt(Integer::parseInt).toArray();
        int flag = 1;
        for (var id : ids) {
          if (employeeDao.delEmployee(id) <= 0) {
            flag = 0;//删除失败
          }
        }
        out.print(flag);
      }
    }
  }
}
