package com.group6.backend.servlet;

import com.google.gson.Gson;
import com.group6.backend.dao.DocumentDao;
import com.group6.backend.dao.impl.DocumentDaoImpl;
import com.group6.backend.pojo.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/documentList.action"})
public class DocumentServlet extends HttpServlet {
  DocumentDao documentDao= new DocumentDaoImpl();
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out=response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "documentList.action"->{
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        String name = request.getParameter("name");
        var list = documentDao.documentList(name,page,limit);
        R r = new R();
        r.put("msg", "查询成功");
        r.put("data", list);
        r.put("count", documentDao.countDocument());
        r.put("code", 0);
        out.print(new Gson().toJson(r));
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
