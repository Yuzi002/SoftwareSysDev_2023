package com.group6.backend.dao.impl;

import com.group6.backend.dao.DocumentDao;
import com.group6.backend.pojo.Document;
import com.group6.backend.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DocumentDaoImpl extends JDBCUtils<Document> implements DocumentDao {
  @Override
  public List<Document> documentList(String name, int page, int limit) {
    var sql = "select d.*,u.`USERNAME` user_name from document_inf d,user_inf u where d.user_ID=u.ID";
    if (name != null && !"".equals(name)) {
      sql += " and NAME like '%" + name + "%'";
    }
    sql += " limit " + (page - 1) * limit + "," + limit;
    return query(sql);
  }

  @Override
  public List<Document> getAllDocument() {
    return null;
  }

  @Override
  public int countDocument() {
    var documents = query("select d.*,u.`USERNAME` user_name from document_inf d,user_inf u where d.user_ID=u.ID");
    return documents.size();
  }

  @Override
  public int addDocument(String title, String content, String filename, int user_id) {
    return update("insert into document_inf values (null,?,?,?,?,?,?)", title, filename, "ppt", content, new Date(), user_id);
  }

  @Override
  public List<Document> getDocumentById(int id) {
    return query("select d.*,u.`USERNAME` user_name from document_inf d,user_inf u where d.user_ID=u.ID and d.id=?", id);
  }

  @Override
  public int updDocument(int id, String title, String remark, String fileName) {
    return update("update document_inf set title=?,filename=?,REMARK=?,CREATE_DATE=? where ID=?",
      title,fileName,remark,new Date(),id);
  }

  @Override
  public Document getBean(ResultSet rs) {
    Document document = new Document();
    try {
      document.setId(rs.getInt("id"));
      document.setTitle(rs.getString("title"));
      document.setRemark(rs.getString("remark"));
      document.setFileName(rs.getString("fileName"));
      document.setFileType(rs.getString("fileType"));
      document.setUserId(rs.getInt("user_id"));
      document.setUserName(rs.getString("user_name"));
      document.setCreateDate(rs.getDate("create_date"));
      return document;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
