package com.Yuzi.web.dao.impl;

import com.Yuzi.web.dao.UserDao;
import com.Yuzi.web.pojo.User;
import com.Yuzi.web.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*
 *
 * */
public class UserDaoImpl extends JDBCUtils<User> implements UserDao {
  //    登录方法-JDBC操作-查询
  @Override
  public User login(String username, String password) {
    List<User> users = query("select * from user_inf where loginname = ? and password = ?", username, password);
    if (users.size() > 0) {
      return users.get(0);
    }
    return null;
  }

  //查询user_inf表中的信息
  @Override
  public List<User> userList(String loginname, String status, int page, int limit) {
    String sql = "select * from user_inf where 1=1";//万能条件
    //对条件做判断，不为空
    if (loginname != null && !"".equals(loginname)) {
      sql += " and loginname like '%" + loginname + "%'";
    }
    if (status != null && !"".equals(status)) {
      sql += " and status = " + status;
    }
    sql += " limit " + (page - 1) * limit + "," + limit;
    return query(sql);
  }

  @Override
  public boolean checkName(String loginname) {
    var users = query("select * from user_inf where loginname = ?", loginname);
    if (users.size() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public int addUser(User user) {
    return update("insert into user_inf values(null,?,?,?,CURRENT_TIMESTAMP,?)", user.getLoginname(), user.getPassword(), user.getStatus(), user.getUsername());
  }

  @Override
  public int updUser(User user) {
    return update("update user_inf set loginname=?,username=?,status=? where id=?", user.getLoginname(), user.getUsername(), user.getStatus(), user.getId());
  }

  //获取用户数量
  @Override
  public int countUser() {
    var users = query("select * from user_inf");
    return users.size();
  }

  @Override
  public int delUser(int id) {
    return update("delete from user_inf where id=?",id);
  }


  //    返回结果集对象
  @Override
  public User getBean(ResultSet rs) {
    User user = new User();
//        将数据从返回结果中去取出，
    try {
      user.setId(rs.getInt("ID"));
      user.setLoginname(rs.getString("loginname"));
      user.setPassword(rs.getString("PASSWORD"));
      user.setStatus(rs.getInt("status"));
      user.setCreatedate(rs.getDate("createdate"));
      user.setUsername(rs.getString("username"));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return user;
  }

}
