package com.Yuzi.web.servlet;

import com.Yuzi.web.dao.UserDao;
import com.Yuzi.web.dao.impl.UserDaoImpl;
import com.Yuzi.web.pojo.User;
import com.Yuzi.web.pojo.R;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/userlist.action"})
public class UserServlet extends HttpServlet {
  //先创建userDao
  private UserDao userDao=new UserDaoImpl();
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out=response.getWriter();
    String loginname=request.getParameter("username");
    String status=request.getParameter("status");
  //查询方法
    List<User> users=userDao.userList(loginname,status);
    R r=new R();
    r.put("msg","查询成功");
    r.put("data",users);
    r.put("count",0);
    r.put("code",0);
    out.print(new Gson().toJson(r));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) {

  }
}
