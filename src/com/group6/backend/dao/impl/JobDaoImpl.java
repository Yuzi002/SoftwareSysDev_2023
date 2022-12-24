package com.group6.backend.dao.impl;

import com.group6.backend.dao.JobDao;
import com.group6.backend.pojo.Employee;
import com.group6.backend.pojo.Job;
import com.group6.backend.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JobDaoImpl extends JDBCUtils<Job> implements JobDao {


  @Override
  public Job getBean(ResultSet rs) {
    Job job = new Job();
//        将数据从返回结果中去取出，
    try {
      job.setId(rs.getInt("ID"));
      job.setName(rs.getString("NAME"));
      job.setRemark(rs.getString("REMARK"));
      job.setState(rs.getInt("state"));
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return job;
  }

  @Override
  public List<Job> jobList(String name, int page, int limit) {
    String sql = "select * from job_inf where 1=1";//万能条件
    //对条件做判断，不为空
    if (name != null && !"".equals(name)) {
      sql += " and NAME like '%" + name + "%'";
    }
    sql += " limit " + (page - 1) * limit + "," + limit;
    return query(sql);
  }

  @Override
  public List<Job> getAllJob() {
    return query("select * from job_inf");
  }

  @Override
  public int countJob() {
    var users = query("select * from job_inf");
    return users.size();
  }

  @Override
  public boolean checkJobName(String name) {
    var jobs = query("select * from job_inf where NAME = ?", name);
    if (jobs.size() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public int addJob(String name, String remark) {
    return update("insert into job_inf values(null,?,?,?)", name, remark, 0);
  }

  @Override
  public int updJob(int id, String name, String remark) {
    return update("update job_inf set NAME=?,REMARK=? where ID=?", name, remark, id);
  }

  @Override
  public int delJob(int id) {
    List<Employee> employeeList = new EmployeeDaoImpl().getEmployeeFromJobId(id);
    if (update("delete from job_inf where ID=?", id) > 0) {
      //级联改变Employee中的Job为默认id，即0
      if (employeeList.size() > 0) {
        for (var employee : employeeList) {
          update("update employee_inf set job_id=0 where id=?", employee.getId());
        }
      }
      return 1;
    } else {
      return 0;
    }
  }
}
