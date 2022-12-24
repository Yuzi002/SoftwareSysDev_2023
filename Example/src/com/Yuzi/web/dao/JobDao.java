package com.Yuzi.web.dao;

import com.Yuzi.web.pojo.Job;

import java.util.List;

public interface JobDao {

  List<Job> jobList(String name, int page, int limit);

  public List<Job> getAllJob();

  int countJob();

  boolean checkJobName(String name);

  int addJob(String name, String remark);

  int updJob(int id, String name, String remark);

  int delJob(int id);
}
