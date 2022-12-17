package com.Yuzi.web.dao;

import com.Yuzi.web.pojo.User;

import java.util.List;

public interface UserDao {
  //定义登录方法
  public User login(String username, String password);

  //查询方法
  public List<User> userList(String loginname, String status);
}
