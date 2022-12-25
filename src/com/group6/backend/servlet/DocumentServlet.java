package com.group6.backend.servlet;

import com.google.gson.Gson;
import com.group6.backend.dao.DocumentDao;
import com.group6.backend.dao.impl.DocumentDaoImpl;
import com.group6.backend.pojo.R;
import com.group6.backend.pojo.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/documentList.action", "/uploadNew.action", "/addDocument.action","/uploadEdit.action", "/updDocument.action"})
public class DocumentServlet extends HttpServlet {
  DocumentDao documentDao = new DocumentDaoImpl();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "documentList.action" -> {
        int page = Integer.parseInt(request.getParameter("page"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        String name = request.getParameter("name");
        var list = documentDao.documentList(name, page, limit);
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
    PrintWriter out = response.getWriter();
    String uri = request.getRequestURI();
    String action = uri.substring((uri.lastIndexOf("/") + 1));
    switch (action) {
      case "uploadNew.action" -> {
        upload(true,request,response,out);
      }
      case "uploadEdit.action" -> {
        upload(false,request,response,out);
      }
      case "addDocument.action" -> {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String filename = request.getParameter("filename");
        User user = (User) request.getSession().getAttribute("user");
        if (filename != null && !filename.equals("")) {
          if (documentDao.addDocument(title, content, filename, user.getId()) > 0) {
            out.print(1);//添加成功
          } else {
            out.print(0);
          }
        } else {
          out.print(-1);//没有文件名，没有文件
        }
      }
      case "updDocument.action" -> {
        String title = request.getParameter("title");
        String remark = request.getParameter("remark");
        String fileName = request.getParameter("filename");
        int id = Integer.parseInt(request.getParameter("id"));
        var documents = documentDao.getDocumentById(id);
        String savePath = request.getSession().getServletContext().getRealPath("/files");
        if (documents.size() == 0) {
          out.print(0);//id不对
          return;
        }
        var document = documents.get(0);
        if (!document.getFileName().equals(fileName)) {//文件名改了，删掉旧文件
          File oldFile = new File( savePath,document.getFileName());
          oldFile.delete();
        }
        if (documentDao.updDocument(id, title, remark, fileName) > 0) {
          out.print(1);//修改成功
        } else {
          out.print(0);
        }
      }
    }
  }
  protected void upload(boolean newFlag,HttpServletRequest request, HttpServletResponse response,PrintWriter out){
    if (ServletFileUpload.isMultipartContent(request)) {
      //得到上传文件的保存目录，将上传的文件存放于files目录下
      String savePath = request.getSession().getServletContext().getRealPath("/files");
      File file = new File(savePath);
      //判断上传文件的保存目录是否存在
      if (!file.exists() && !file.isDirectory()) {
        System.out.println(savePath + "目录不存在，需要创建");
        //创建目录
        file.mkdirs();
      }
      //1、创建一个DiskFileItemFactory工厂
      DiskFileItemFactory factory = new DiskFileItemFactory();
      //2、创建一个文件上传解析器
      ServletFileUpload upload = new ServletFileUpload(factory);
      //解决上传文件名的中文乱码
      upload.setHeaderEncoding("UTF-8");
      try {
        //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item : items) {
          //如果fileitem中封装的是普通输入项的数据
          if (item.isFormField()) {
            String name = item.getFieldName();
            //解决普通输入项的数据的中文乱码问题
            String value = item.getString("UTF-8");
            System.out.println(name + "=" + value);
          } else {//如果fileitem中封装的是上传文件
            //得到上传的文件名称，
            String filename = item.getName();
            System.out.println(filename);
            if (filename == null || filename.trim().equals("")) {
              continue;
            }
            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
            filename = filename.substring(filename.lastIndexOf("\\") + 1);
            File file1 = new File(savePath, filename);
            if (file1.exists()&&newFlag) {//文件已存在，若是新上传，则报错拒绝
              //out.print(-2);
            } else {
              item.write(file1);
              Map<String, Object> map = new HashMap<>();
              map.put("url", filename);
              out.print(new Gson().toJson(map));
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
