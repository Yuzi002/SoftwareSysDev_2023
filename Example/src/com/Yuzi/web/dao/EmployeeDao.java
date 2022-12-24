package com.Yuzi.web.dao;

import com.Yuzi.web.pojo.Employee;

import java.util.List;

public interface EmployeeDao {
  List<Employee> getEmployeeList(Employee employee);

  boolean selectCardId(String id);

  int addEmployee(Employee employee);

  int countEmployee();
}
