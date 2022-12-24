package com.Yuzi.web.servlet;

import com.Yuzi.web.dao.JobDao;
import com.Yuzi.web.dao.impl.JobDaoImpl;
import com.Yuzi.web.pojo.Job;
import com.Yuzi.web.pojo.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/jobList.action","/getAllJob.action", "/checkJobName.action", "/addJob.action", "/updJob.action", "/delJob.action", "/delJobs.action"})
public class JobServlet extends HttpServlet {
  private final JobDao jobDao = new JobDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring(uri.lastIndexOf("/") + 1);
    switch (action) {
      case "jobList.action" -> {
        String name = request.getParameter("name");
        //当前页数
        int page = Integer.parseInt(request.getParameter("page"));
        //每页多少条
        int limit = Integer.parseInt(request.getParameter("limit"));
        //查询方法
        List<Job> jobs = jobDao.jobList(name, page, limit);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", jobs);
        r.put("count", jobDao.countJob());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
      }
      case "getAllJob.action"->{
        out.print(new Gson().toJson(jobDao.getAllJob()));
      }
      case "checkJobName.action" -> {
        String name = request.getParameter("name");
        if (jobDao.checkJobName(name)) {
          out.print(0);//用户已存在
        } else {
          out.print(1);
        }
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring(uri.lastIndexOf("/") + 1);
    switch (action) {
      case "addJob.action" -> {
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        if (jobDao.addJob(name, remark) > 0) {
          out.print(1);//添加成功
        } else {
          out.print(0);
        }
      }
      case "updJob.action" -> {
        String name = request.getParameter("name");
        String remark = request.getParameter("remark");
        int id = Integer.parseInt(request.getParameter("id"));
        if (jobDao.updJob(id, name, remark) > 0) {
          out.print(1);//修改成功
        } else {
          out.print(0);
        }
      }
      case "delJob.action" -> {
        int id = Integer.parseInt(request.getParameter("id"));
        //TODO:级联删除
        if (jobDao.delJob(id) > 0) {
          out.print(1);//删除成功
        } else {
          out.print(0);
        }
      }
      case "delJobs.action" -> {
        //TODO:级联删除
        var ids = Arrays.stream(request.getParameter("ids").split(",")).mapToInt(Integer::parseInt).toArray();
        int flag = 1;
        for (var id : ids) {
          if (jobDao.delJob(id) <= 0) {
            flag = 0;//删除失败
          }
        }
        out.print(flag);
      }
    }
  }
}
