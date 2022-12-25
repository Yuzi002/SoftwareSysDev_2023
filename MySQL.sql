-- MySQL dump 10.13  Distrib 8.0.31, for Linux (x86_64)
--
-- Host: localhost    Database: hrm
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dept_inf`
--

DROP TABLE IF EXISTS `dept_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept_inf` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `state` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept_inf`
--

LOCK TABLES `dept_inf` WRITE;
/*!40000 ALTER TABLE `dept_inf` DISABLE KEYS */;
INSERT INTO `dept_inf` VALUES (0,'默认部门','默认部门',0),(1,'技术部','技术部',0),(2,'运营部','运营部',0),(3,'财务部','财务部',0),(5,'总公办','总公办',0),(6,'市场部','市场部',0),(7,'教学部','教学部',0),(15,'高能部','11',0);
/*!40000 ALTER TABLE `dept_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_inf`
--

DROP TABLE IF EXISTS `document_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_inf` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) NOT NULL,
  `filename` varchar(300) NOT NULL,
  `filetype` varchar(100) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USER_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `idx_document_inf_filename` (`filename`),
  KEY `FK_DOCUMENT_USER` (`USER_ID`),
  CONSTRAINT `FK_DOCUMENT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `user_inf` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_inf`
--

LOCK TABLES `document_inf` WRITE;
/*!40000 ALTER TABLE `document_inf` DISABLE KEYS */;
INSERT INTO `document_inf` VALUES (1,'dw89eh','21','ppt','e2','2022-12-25 01:16:56',1),(2,'dw89eh','22','ppt','e2','2022-12-25 01:16:56',1),(10,'test1','test1.txt','ppt','test1','2022-12-25 14:27:10',1);
/*!40000 ALTER TABLE `document_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_inf`
--

DROP TABLE IF EXISTS `employee_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_inf` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `CARD_ID` varchar(18) NOT NULL,
  `ADDRESS` varchar(50) NOT NULL,
  `POST_CODE` varchar(50) DEFAULT NULL,
  `TEL` varchar(16) DEFAULT NULL,
  `PHONE` varchar(11) NOT NULL,
  `QQ_NUM` varchar(10) DEFAULT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `SEX` int NOT NULL DEFAULT '1',
  `PARTY` varchar(10) DEFAULT NULL,
  `BIRTHDAY` timestamp NULL DEFAULT NULL,
  `RACE` varchar(100) DEFAULT NULL,
  `EDUCATION` varchar(10) DEFAULT NULL,
  `SPECIALITY` varchar(20) DEFAULT NULL,
  `HOBBY` varchar(100) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` int DEFAULT '0',
  `dept_id` int DEFAULT NULL,
  `job_id` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_dept_inf` (`dept_id`),
  KEY `FK_job_inf` (`job_id`),
  CONSTRAINT `FK_dept_inf` FOREIGN KEY (`dept_id`) REFERENCES `dept_inf` (`ID`) ON DELETE SET NULL,
  CONSTRAINT `FK_job_inf` FOREIGN KEY (`job_id`) REFERENCES `job_inf` (`ID`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_inf`
--

LOCK TABLES `employee_inf` WRITE;
/*!40000 ALTER TABLE `employee_inf` DISABLE KEYS */;
INSERT INTO `employee_inf` VALUES (1,'爱丽丝','441324198412031122','广州天河','510000','020-77777777','13902001111','36750066','251425887@qq.com',0,'党员','1980-01-01 00:00:00','满','本科','美声','唱歌',NULL,'2016-03-14 11:35:18',0,1,3),(2,'杰克','22623','43234','42427424','42242','4247242','42424','251425887@qq.com',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2016-03-14 11:35:18',0,2,4),(3,'bb','432801197711251038','广州','510000','020-99999999','13907351532','36750064','36750064@qq.com',1,'党员','1977-11-25 00:00:00','汉','本科','计算机','爬山','无','2016-07-14 09:54:52',0,3,5),(4,'test','441324198412031122','sdfas','510000','020-88798783','18932226981','12345678','229490481@qq.com',1,'清白','2020-04-16 00:00:00','汉','本科',NULL,'唱歌888','四大天王888','2020-04-17 11:36:29',0,6,0),(8,'yuzi ren','wfew3','地方的分析','1919810','12345678910','12345678910','3位25','5@q.co',1,'的让更多的','2022-12-25 00:00:00','我','wr3w','sf','sere','sresrfe','2022-12-25 14:14:41',0,6,3);
/*!40000 ALTER TABLE `employee_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_inf`
--

DROP TABLE IF EXISTS `job_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_inf` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `state` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_inf`
--

LOCK TABLES `job_inf` WRITE;
/*!40000 ALTER TABLE `job_inf` DISABLE KEYS */;
INSERT INTO `job_inf` VALUES (0,'默认职位','默认职位',0),(1,'职员','职员',0),(3,'Java中级开发工程师','Java中级开发工程师',0),(4,'Java高级开发工程师','Java高级开发工程师',0),(5,'系统管理员','系统管理员',0),(7,'主管','主管',0),(8,'经理','经理',0),(9,'总经理','总经理',0),(10,'职员a','cccc',0);
/*!40000 ALTER TABLE `job_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice_inf`
--

DROP TABLE IF EXISTS `notice_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice_inf` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type_id` int DEFAULT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `notice_inf_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_inf` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice_inf`
--

LOCK TABLES `notice_inf` WRITE;
/*!40000 ALTER TABLE `notice_inf` DISABLE KEYS */;
INSERT INTO `notice_inf` VALUES (1,'测试888','2020-04-17 15:32:36',1,'<p>test3333999</p>',NULL,NULL);
/*!40000 ALTER TABLE `notice_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_inf`
--

DROP TABLE IF EXISTS `type_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_inf` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `state` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_inf`
--

LOCK TABLES `type_inf` WRITE;
/*!40000 ALTER TABLE `type_inf` DISABLE KEYS */;
INSERT INTO `type_inf` VALUES (1,'新闻2','2020-04-17 14:19:31',0,NULL,'2020-04-19 10:02:10'),(2,'娱乐',NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `type_inf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_inf`
--

DROP TABLE IF EXISTS `user_inf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_inf` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `STATUS` int NOT NULL DEFAULT '1',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_inf`
--

LOCK TABLES `user_inf` WRITE;
/*!40000 ALTER TABLE `user_inf` DISABLE KEYS */;
INSERT INTO `user_inf` VALUES (1,'admin','123456',2,'2016-03-12 09:34:28','超级管理员'),(3,'xi','123456',1,'2020-04-14 14:18:28','希文'),(5,'Yuzi','114514',2,'2022-12-17 08:53:30','Yuzi02'),(6,'Lionaom','123456',2,'2022-12-18 01:40:01','HS'),(7,'situ','123456',2,'2022-12-18 02:54:37','2001'),(12,'w2w','123456',1,'2022-12-25 06:24:22','e2e2r');
/*!40000 ALTER TABLE `user_inf` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-25 14:45:00
