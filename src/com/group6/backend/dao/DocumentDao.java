package com.group6.backend.dao;

import com.group6.backend.pojo.Document;

import java.util.List;

public interface DocumentDao {
  List<Document> documentList(String name, int page, int limit);

  List<Document> getAllDocument();

  int countDocument();

  int delDocument(int id);
}
