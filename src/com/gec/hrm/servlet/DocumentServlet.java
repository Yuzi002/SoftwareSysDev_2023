package com.gec.hrm.servlet;

import com.gec.hrm.dao.DocumentDao;
import com.gec.hrm.dao.impl.DocumentDaoImpl;
import com.gec.hrm.pojo.Document;
import com.gec.hrm.pojo.R;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.print.Doc;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/documentList.action","/updDocument.action","/upload.action"})
public class DocumentServlet extends HttpServlet {
    DocumentDao documentDao= new DocumentDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
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
            case "upload.action"->{
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if(isMultipart){
                    String realpath =request.getSession().getServletContext().getRealPath("/files");
                    File dir = new File(realpath);
                    if(!dir.exists()) dir.mkdirs();

                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setHeaderEncoding("UTF-8");


                    try {
                        List<FileItem> items = upload.parseRequest(request);
                        for(FileItem item:items){
                            if(item.isFormField()){
                                String name1= item.getFieldName();
                                String value = item.getString("UTF-8");
                            }else {
                                String urlString = System.currentTimeMillis()+
                                        item.getName().substring(item.getName().lastIndexOf("."));
                                item.write(new File(dir,urlString));
                                Map<String,Object> map = new HashMap<>();
                                map.put("url",urlString);
                                out.print(new Gson().toJson(map));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String uri = request.getRequestURI();
        String action = uri.substring((uri.lastIndexOf("/") + 1));
        switch (action){
            case "updDocument.action"->{
                String title = request.getParameter("title");
                String remark = request.getParameter("remark");
                String fileName = request.getParameter("filename");
                String[] filetype = fileName.split("");
                int id = Integer.parseInt(request.getParameter("id"));
                Document document = new Document(id,title,remark,fileName,filetype,new Date());
                if (documentDao.updDocument(document) > 0) {
                    out.print(1);//修改成功
                } else {
                    out.print(0);
                }
                break;
            }
        }
    }
}
