package com.group6.backend.servlet;

import com.group6.backend.dao.DeptDao;
import com.group6.backend.dao.impl.DeptDaoImpl;
import com.group6.backend.pojo.Dept;
import com.group6.backend.pojo.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/deptList.action", "/getAllDept.action", "/updDept.action", "/addDept.action", "/checkDeptName.action", "/delDept.action", "/delDepts.action"})
public class DeptServlet extends HttpServlet {
  private final DeptDao deptDao = new DeptDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "deptList.action" -> {
        String name = request.getParameter("name");
        //当前页数
        int page = Integer.parseInt(request.getParameter("page"));
        //每页多少条
        int limit = Integer.parseInt(request.getParameter("limit"));
        //查询方法
        List<Dept> depts = deptDao.deptlist(name, page, limit);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", depts);
        r.put("count", deptDao.countDept());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
      }
      case "getAllDept.action" -> {
        out.print(new Gson().toJson(deptDao.getAllDept()));
      }
      case "checkDeptName.action" -> {
        String name = request.getParameter("name");
        if (deptDao.checkDeptName(name)) {
          out.print(0);//用户已存在
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
      case "addDept.action" -> {
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        if (deptDao.addDept(new Dept(name, remark)) > 0) {
          out.print(1);//添加成功
        } else {
          out.print(0);
        }
      }
      case "updDept.action" -> {
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        int id = Integer.parseInt(request.getParameter("id"));
        if (deptDao.updDept(new Dept(id, name, remark)) > 0) {
          out.print(1);//修改成功
        } else {
          out.print(0);
        }
      }
      case "delDept.action" -> {
        int id = Integer.parseInt(request.getParameter("id"));
        //TODO:级联删除
        if (deptDao.delDept(id) > 0) {
          out.print(1);//删除成功
        } else {
          out.print(0);
        }
      }
      case "delDepts.action" -> {
        //TODO:级联删除
        var ids = Arrays.stream(request.getParameter("ids").split(",")).mapToInt(Integer::parseInt).toArray();
        int flag = 1;
        for (var id : ids) {
          if (deptDao.delDept(id) <= 0) {
            flag = 0;//删除失败
          }
        }
        out.print(flag);
      }
    }
  }
}
