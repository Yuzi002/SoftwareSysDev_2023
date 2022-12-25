package com.group6.backend.pojo;

import java.util.Date;

public class Document {
  private int id;
  private String title;
  private String fileName;
  private String fileType;
  private String fileURL;
  private String remark;
  private Date createDate;
  private int userId;
  private String userName;


  public Document() {
  }

  public Document(int id, String title, String fileName, String fileType, String fileURL, String remark, Date createDate) {
    this.id = id;
    this.title = title;
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileURL = fileURL;
    this.remark = remark;
    this.createDate = createDate;
  }

  public Document(int id, String title, String fileName, String fileType, String fileURL, String remark, Date createDate, int userId, String userName) {
    this.id = id;
    this.title = title;
    this.fileName = fileName;
    this.fileType = fileType;
    this.fileURL = fileURL;
    this.remark = remark;
    this.createDate = createDate;
    this.userId = userId;
    this.userName = userName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileURL() {
    return fileURL;
  }

  public void setFileURL(String fileURL) {
    this.fileURL = fileURL;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
