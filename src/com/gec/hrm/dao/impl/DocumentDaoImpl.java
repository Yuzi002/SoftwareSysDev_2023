package com.gec.hrm.dao.impl;

import com.gec.hrm.dao.DocumentDao;
import com.gec.hrm.dao.UserDao;
import com.gec.hrm.pojo.Document;
import com.gec.hrm.util.JDBCUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public int updDocument(Document document) {
        return update("update document_inf set title=?,filename=?,REMARK=?,CREATE_DATE=? where ID=?",
                document.getTitle(),document.getFileName(),document.getRemark(),document.getCreateDate(),document.getId());
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
            document.setCreateDate(rs.getDate("CREATE_DATE"));
            return document;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
