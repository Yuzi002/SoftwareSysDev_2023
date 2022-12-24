package com.group6.backend.servlet;

import com.group6.backend.dao.UserDao;
import com.group6.backend.dao.impl.UserDaoImpl;
import com.group6.backend.pojo.R;
import com.group6.backend.pojo.User;
import com.google.gson.Gson;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/userlist.action", "/checkName.action", "/addUser.action", "/updUser.action", "/delUser.action", "/delUsers.action"})
public class UserServlet extends HttpServlet {
  //先创建userDao
  private UserDao userDao = new UserDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    String uri = request.getRequestURI();
    String action = uri.substring(uri.lastIndexOf("/") + 1);
    switch (action) {
      case "userlist.action" -> {
        userlist(request, response);
      }
      case "checkName.action" -> {
        checkName(request, response);
      }
    }
  }

  //检查用户名是否重复
  private void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter out = response.getWriter();
    String loginname = request.getParameter("loginname");
    if (userDao.checkName(loginname)) {
      out.print(0);//用户已存在
    } else {
      out.print(1);
    }
  }

  //查询用户
  private void userlist(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PrintWriter out = response.getWriter();
    String loginname = request.getParameter("username");
    String status = request.getParameter("status");
    //当前页数
    int page = Integer.parseInt(request.getParameter("page"));
    //每页多少条
    int limit = Integer.parseInt(request.getParameter("limit"));
    //查询方法
    List<User> users = userDao.userList(loginname, status, page, limit);
    R r = new R();
    r.put("msg", "查询成功");
    r.put("data", users);
    r.put("count", userDao.countUser());
    r.put("code", 0);
    out.print(new Gson().toJson(r));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring(uri.lastIndexOf("/") + 1);
    switch (action) {
      case "addUser.action" -> {
        String loginname = request.getParameter("loginname");
        String username = request.getParameter("username");
        int status = Integer.parseInt(request.getParameter("status"));
        String password = request.getParameter("password");
        if (userDao.addUser(new User(loginname, password, status, username)) > 0) {
          out.print(1);//添加成功
        } else {
          out.print(0);
        }
      }
      case "updUser.action" -> {
        String loginname = request.getParameter("loginname");
        String username = request.getParameter("username");
        int status = Integer.parseInt(request.getParameter("status"));
        int id = Integer.parseInt(request.getParameter("id"));
        if (userDao.updUser(new User(id, loginname, status, username)) > 0) {
          out.print(1);//修改成功
        } else {
          out.print(0);
        }
      }
      case "delUser.action" -> {
        int id = Integer.parseInt(request.getParameter("id"));
        //判断是不是删的自己
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getId() == id) {
          out.print(id);//是自己
          break;
        }
        if (userDao.delUser(id) > 0) {
          out.print(0);//修改成功
        } else {
          out.print(-1);//修改失败
        }
      }
      case "delUsers.action" -> {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        var ids = Arrays.stream(request.getParameter("ids").split(",")).mapToInt(Integer::parseInt).toArray();
        if (ArrayUtils.contains(ids, user.getId())) {//有本人，错误
          out.print(user.getId());
        } else {
          int flag = 0;
          for (var id : ids) {
            if (userDao.delUser(id) <= 0) {
              flag = -1;//删除失败
            }
          }
          out.print(flag);
        }
      }
    }

  }
}
