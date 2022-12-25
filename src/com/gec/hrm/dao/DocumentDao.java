package com.gec.hrm.dao;

import com.gec.hrm.pojo.Document;

import java.util.List;

public interface DocumentDao {
    List<Document> documentList(String name, int page, int limit);

    List<Document> getAllDocument();

    int countDocument();

    int updDocument(Document document);
}
