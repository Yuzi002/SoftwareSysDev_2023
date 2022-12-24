package com.group6.backend.dao;

import com.group6.backend.pojo.Dept;

import java.util.List;

//定义对dept_inf表的操作方法
public interface DeptDao {
  public List<Dept> deptlist(String name, int page, int limit);

  public List<Dept> getAllDept();

  public int updDept(Dept dept);

  public int countDept();

  int addDept(Dept dept);

  boolean checkDeptName(String name);

  int delDept(int id);
}
