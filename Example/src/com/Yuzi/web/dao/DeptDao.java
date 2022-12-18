package com.Yuzi.web.dao;

import com.Yuzi.web.pojo.Dept;

import java.util.List;

//定义对dept_inf表的操作方法
public interface DeptDao {
  public List<Dept> deptlist(String name, int page, int limit);

  public int updDept(Dept dept);

  public int countDept();

  int addDept(Dept dept);

  boolean checkDeptName(String name);

  int delDept(int id);
}
