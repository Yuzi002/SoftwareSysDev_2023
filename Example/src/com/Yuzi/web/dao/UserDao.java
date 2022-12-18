package com.Yuzi.web.dao;

import com.Yuzi.web.pojo.User;

import java.util.List;

public interface UserDao {
  //定义登录方法
  public User login(String username, String password);

  //查询方法
  public List<User> userList(String loginname, String status, int page, int limit);

  boolean checkName(String loginname);

  int addUser(User user);

  int updUser(User user);

  int countUser();

  int delUser(int id);
}
