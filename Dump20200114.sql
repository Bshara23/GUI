CREATE DATABASE  IF NOT EXISTS `icm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `icm`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: icm
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `changerequest`
--

DROP TABLE IF EXISTS `changerequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `changerequest` (
  `requestID` bigint(8) NOT NULL,
  `username` varchar(256) NOT NULL,
  `startDateOfRequest` timestamp NOT NULL,
  `estimatedTimeForExecution` timestamp NOT NULL,
  `endDateOfRequest` timestamp NOT NULL,
  `commentsLT` longtext NOT NULL,
  `requestDescriptionLT` longtext NOT NULL,
  `descriptionOfRequestedChangeLT` longtext NOT NULL,
  `descriptionOfCurrentStateLT` longtext NOT NULL,
  `relatedInformationSystem` varchar(256) NOT NULL,
  PRIMARY KEY (`requestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changerequest`
--

LOCK TABLES `changerequest` WRITE;
/*!40000 ALTER TABLE `changerequest` DISABLE KEYS */;
INSERT INTO `changerequest` VALUES (1,'userName10','2020-01-12 14:14:16','1999-01-01 02:30:00','1999-01-01 02:30:00','daw','awd','ad','wad','Information System'),(2,'userName10','2020-01-12 14:17:45','1999-01-01 02:30:00','1999-01-01 02:30:00','awd','awd','awd','awd','Information System'),(3,'userName10','2020-01-12 14:21:59','1999-01-01 02:30:00','1999-01-01 02:30:00','awdawd','daw','awd','dawd','Information System'),(4,'userName10','2020-01-12 14:24:32','1999-01-01 02:30:00','1999-01-01 02:30:00','dawdawd','daw','awdaw','dawd','Information System'),(5,'userName10','2020-01-12 14:25:36','1999-01-01 02:30:00','1999-01-01 02:30:00','dawdwa','dawd','wdaw','awda','Information System'),(6,'userName10','2020-01-12 14:30:23','1999-01-01 02:30:00','1999-01-01 02:30:00','awdwad','adw','awdwad','dawd','Information System'),(7,'userName10','2020-01-12 16:04:06','1999-01-01 02:30:00','1999-01-01 02:30:00','awd','awd','awd','awd','Information System'),(8,'userName10','2020-01-13 14:29:03','1999-01-01 02:30:00','1999-01-01 02:30:00','3wrw3r','r3w','w3r3wr','3wr','Information System'),(9,'userName10','2020-01-13 14:44:13','1999-01-01 02:30:00','1999-01-01 02:30:00','gdrgd','gr','drgdr','gdrg','Information System'),(10,'userName10','2020-01-13 14:52:45','1999-01-01 02:30:00','1999-01-01 02:30:00','j','j','j','jj','Information System'),(11,'userName10','2020-01-13 16:23:05','1999-01-01 02:30:00','1999-01-01 02:30:00','ret','ret','ret','ret','Information System');
/*!40000 ALTER TABLE `changerequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `empNumber` bigint(8) NOT NULL,
  `empDepartment` varchar(256) NOT NULL,
  `organizationalRole` varchar(256) NOT NULL,
  `userName` varchar(256) NOT NULL,
  PRIMARY KEY (`empNumber`),
  KEY `userNameEmployeeFK` (`userName`),
  CONSTRAINT `userNameEmployeeFK` FOREIGN KEY (`userName`) REFERENCES `systemuser` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (-1,'System','System','System'),(10,'Software','Role10','username10'),(11,'Software','Role11','username11'),(12,'Software','Role12','username12'),(13,'Software','Role13','username13'),(14,'Software','Role14','username14'),(15,'Software','Role15','username15'),(16,'Software','Role16','username16'),(17,'Software','Role17','username17'),(18,'Software','Role18','username18'),(19,'Software','Role19','username19'),(20,'Software','Role20','username20'),(21,'Software','Role21','username21'),(22,'Software','Role22','username22');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluationreport`
--

DROP TABLE IF EXISTS `evaluationreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluationreport` (
  `result` varchar(256) NOT NULL,
  `constraints` varchar(256) NOT NULL,
  `risks` varchar(256) NOT NULL,
  `estimatedExecutionTime` timestamp NOT NULL,
  `reportID` bigint(8) NOT NULL,
  `phaseID` bigint(8) NOT NULL,
  `contentLT` longtext NOT NULL,
  `place` varchar(256) NOT NULL,
  PRIMARY KEY (`reportID`),
  KEY `requestIDEvaluationReportFK_idx` (`phaseID`),
  CONSTRAINT `requestIDEvaluationReportFK` FOREIGN KEY (`phaseID`) REFERENCES `phase` (`phaseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluationreport`
--

LOCK TABLES `evaluationreport` WRITE;
/*!40000 ALTER TABLE `evaluationreport` DISABLE KEYS */;
INSERT INTO `evaluationreport` VALUES ('j','j','j','2020-01-28 01:30:00',1,8,'j','j'),('u','u','u','2020-01-29 01:30:00',2,10,'u','i');
/*!40000 ALTER TABLE `evaluationreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executionchangescommitteemember`
--

DROP TABLE IF EXISTS `executionchangescommitteemember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executionchangescommitteemember` (
  `isManager` tinyint(1) NOT NULL,
  `empNumber` bigint(8) NOT NULL,
  PRIMARY KEY (`empNumber`),
  CONSTRAINT `empNumberExecutionChangesCommitteeMemberFK` FOREIGN KEY (`empNumber`) REFERENCES `employee` (`empNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executionchangescommitteemember`
--

LOCK TABLES `executionchangescommitteemember` WRITE;
/*!40000 ALTER TABLE `executionchangescommitteemember` DISABLE KEYS */;
INSERT INTO `executionchangescommitteemember` VALUES (0,10),(0,21),(1,22);
/*!40000 ALTER TABLE `executionchangescommitteemember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executionreport`
--

DROP TABLE IF EXISTS `executionreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executionreport` (
  `reportID` bigint(8) NOT NULL,
  `phaseID` bigint(8) NOT NULL,
  `contentLT` longtext NOT NULL,
  `place` varchar(256) NOT NULL,
  PRIMARY KEY (`reportID`),
  KEY `requestIDExecutionReportFK_idx` (`phaseID`),
  CONSTRAINT `requestIDExecutionReportFK` FOREIGN KEY (`phaseID`) REFERENCES `phase` (`phaseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executionreport`
--

LOCK TABLES `executionreport` WRITE;
/*!40000 ALTER TABLE `executionreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `executionreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `ID` bigint(8) NOT NULL AUTO_INCREMENT,
  `requestID` bigint(8) NOT NULL,
  `data` mediumblob,
  `fileName` varchar(256) NOT NULL,
  `type` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `requestIDFileFK` (`requestID`),
  CONSTRAINT `requestIDFileFK` FOREIGN KEY (`requestID`) REFERENCES `changerequest` (`requestID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manager` (
  `userName` varchar(45) NOT NULL,
  PRIMARY KEY (`userName`),
  CONSTRAINT `managerFK` FOREIGN KEY (`userName`) REFERENCES `systemuser` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('username10');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `messageID` bigint(8) NOT NULL AUTO_INCREMENT,
  `subject` varchar(256) NOT NULL,
  `from` varchar(256) NOT NULL,
  `to` varchar(256) NOT NULL,
  `messageContentLT` longtext NOT NULL,
  `hasBeenViewed` tinyint(1) NOT NULL,
  `sentAt` timestamp NOT NULL,
  `isStarred` tinyint(1) NOT NULL,
  `isRead` tinyint(1) NOT NULL,
  `isArchived` tinyint(1) NOT NULL,
  `requestId` bigint(8) DEFAULT '-1',
  `phaseId` bigint(8) DEFAULT '-1',
  PRIMARY KEY (`messageID`),
  KEY `userNameMessageFK` (`to`),
  KEY `userNameMessageFKFrom_idx` (`from`),
  CONSTRAINT `userNameMessageFK` FOREIGN KEY (`to`) REFERENCES `systemuser` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userNameMessageFKFrom` FOREIGN KEY (`from`) REFERENCES `systemuser` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=54420 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (8910,'subject13','username13','username2','messageContent13',0,'2020-01-02 22:00:00',0,0,0,-1,-1),(18731,'subject9','username9','username2','messageContent9',0,'2019-12-31 22:00:00',0,0,0,-1,-1),(20249,'subject11','username11','username2','messageContent11',0,'2019-12-31 22:00:00',0,0,0,-1,-1),(40089,'subject16','username16','username2','messageContent16',1,'2020-02-02 03:03:00',1,1,1,-1,-1),(53316,'subject0','username1','username2','messageContent0',1,'2020-02-07 22:00:00',0,1,1,-1,-1),(54254,'subject21','username21','username2','messageContent21',0,'2020-02-08 22:00:00',1,0,0,-1,-1),(54255,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 10:09:03',0,0,0,1,-1),(54256,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 10:43:41',0,0,0,1,-1),(54257,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 10:49:56',0,0,0,989911,1),(54258,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 10:50:20',0,0,0,989912,1),(54259,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 10:51:28',0,0,0,989913,17),(54260,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 13:58:40',1,0,0,989914,18),(54261,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-10 14:14:25',0,0,0,989915,19),(54262,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989915].\nPlease choose the required time to evaluate the request',0,'2020-01-11 11:18:10',0,0,0,989915,19),(54263,'A phase is pending on deadline confirmation','System','username10','The phase [19] is waiting for a confirmation for the deadline!',0,'2020-01-11 20:22:12',0,0,0,989915,19),(54264,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-11 20:33:06',0,0,0,989916,20),(54265,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-11 20:35:08',0,0,0,989917,21),(54266,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [20] has been rejected!',0,'2020-01-11 20:38:32',0,0,0,989916,20),(54267,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-11 20:38:55',0,0,0,989918,22),(54268,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [22] has been rejected!',0,'2020-01-11 20:39:19',0,0,0,989918,22),(54269,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-11 20:41:40',0,0,0,989919,23),(54270,'Set required time to evaluate a request','System','username15','You have been assigned to evaluate the request[989919].\nPlease choose the required time to evaluate the request',0,'2020-01-11 20:46:20',0,0,0,989919,23),(54271,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-11 20:46:56',0,0,0,989920,24),(54272,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989920].\nPlease choose the required time to evaluate the request',0,'2020-01-11 20:47:13',0,0,0,989920,24),(54273,'A phase is pending on deadline confirmation','System','username10','The phase [24] is waiting for a confirmation for the deadline!',0,'2020-01-11 20:47:43',0,0,0,989920,24),(54274,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [24] has been accepted!',0,'2020-01-11 20:48:06',0,0,0,989920,24),(54275,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [24] has been accepted!',0,'2020-01-11 20:49:01',0,0,0,989920,24),(54276,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [24] has been accepted!',0,'2020-01-11 20:52:43',0,0,0,989920,24),(54277,'A request is waiting for decision','System','username20','The requests [989920] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:36:37',0,0,0,989920,25),(54278,'A request is waiting for decision','System','username21','The requests [989920] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:36:37',0,0,0,989920,25),(54279,'A request is waiting for decision','System','username22','The requests [989920] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:36:37',0,0,0,989920,25),(54280,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 11:44:19',0,0,0,989921,26),(54281,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989921].\nPlease choose the required time to evaluate the request',0,'2020-01-12 11:44:55',0,0,0,989921,26),(54282,'A phase is pending on deadline confirmation','System','username10','The phase [26] is waiting for a confirmation for the deadline!',0,'2020-01-12 11:45:22',0,0,0,989921,26),(54283,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [26] has been accepted!',0,'2020-01-12 11:45:44',0,0,0,989921,26),(54284,'A request is waiting for decision','System','username20','The requests [989921] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:46:14',0,0,0,989921,27),(54285,'A request is waiting for decision','System','username21','The requests [989921] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:46:14',0,0,0,989921,27),(54286,'A request is waiting for decision','System','username22','The requests [989921] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:46:14',0,0,0,989921,27),(54287,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 11:47:30',0,0,0,989922,28),(54288,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989922].\nPlease choose the required time to evaluate the request',0,'2020-01-12 11:48:17',0,0,0,989922,28),(54289,'A phase is pending on deadline confirmation','System','username10','The phase [28] is waiting for a confirmation for the deadline!',0,'2020-01-12 11:48:24',0,0,0,989922,28),(54290,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [28] has been accepted!',0,'2020-01-12 11:48:35',0,0,0,989922,28),(54291,'A request is waiting for decision','System','username10','The requests [989922] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:48:55',0,0,0,989922,29),(54292,'A request is waiting for decision','System','username21','The requests [989922] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:48:56',0,0,0,989922,29),(54293,'A request is waiting for decision','System','username22','The requests [989922] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 11:48:56',0,0,0,989922,29),(54294,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 12:32:06',0,0,0,989923,30),(54295,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989923].\nPlease choose the required time to evaluate the request',0,'2020-01-12 12:32:15',0,0,0,989923,30),(54296,'A phase is pending on deadline confirmation','System','username10','The phase [30] is waiting for a confirmation for the deadline!',0,'2020-01-12 12:32:30',0,0,0,989923,30),(54297,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [30] has been accepted!',0,'2020-01-12 12:33:01',0,0,0,989923,30),(54298,'A request is waiting for decision','System','username10','The requests [989923] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 12:49:46',0,0,0,989923,31),(54299,'A request is waiting for decision','System','username21','The requests [989923] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 12:49:46',0,0,0,989923,31),(54300,'A request is waiting for decision','System','username22','The requests [989923] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 12:49:46',0,0,0,989923,31),(54301,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 12:49:58',0,0,0,989924,32),(54302,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989924].\nPlease choose the required time to evaluate the request',0,'2020-01-12 12:50:14',0,0,0,989924,32),(54303,'A phase is pending on deadline confirmation','System','username10','The phase [32] is waiting for a confirmation for the deadline!',0,'2020-01-12 12:50:27',0,0,0,989924,32),(54304,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [32] has been accepted!',0,'2020-01-12 12:50:47',0,0,0,989924,32),(54305,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 989924 has requested a time extension',0,'2020-01-12 12:52:04',0,0,0,989924,32),(54306,'Time extension accepted','System','username10','Your time extension for the request [id:989924] has been accepted!',0,'2020-01-12 12:53:08',0,0,0,-1,-1),(54307,'Time extension accepted','System','username10','Your time extension for the request [id:989924] has been accepted!',0,'2020-01-12 13:03:22',0,0,0,-1,-1),(54308,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:03:39',0,0,0,989925,33),(54309,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989925].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:03:47',0,0,0,989925,33),(54310,'A phase is pending on deadline confirmation','System','username10','The phase [33] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:03:58',0,0,0,989925,33),(54311,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [33] has been accepted!',0,'2020-01-12 13:04:04',0,0,0,989925,33),(54312,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 989925 has requested a time extension',0,'2020-01-12 13:04:18',0,0,0,989925,33),(54313,'Time extension accepted','System','username10','Your time extension for the request [id:989925] has been accepted!',0,'2020-01-12 13:05:50',0,0,0,-1,-1),(54314,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:13:12',0,0,0,989926,34),(54315,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989926].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:13:25',0,0,0,989926,34),(54316,'A phase is pending on deadline confirmation','System','username10','The phase [34] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:13:43',0,0,0,989926,34),(54317,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [34] has been accepted!',0,'2020-01-12 13:14:07',0,0,0,989926,34),(54318,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 989926 has requested a time extension',0,'2020-01-12 13:14:35',0,0,0,989926,34),(54319,'Time extension accepted','System','username10','Your time extension for the request [id:989926] has been accepted!',0,'2020-01-12 13:14:45',0,0,0,-1,-1),(54320,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:17:34',0,0,0,989927,35),(54321,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989927].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:17:39',0,0,0,989927,35),(54322,'A phase is pending on deadline confirmation','System','username10','The phase [35] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:17:54',0,0,0,989927,35),(54323,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [35] has been accepted!',0,'2020-01-12 13:18:01',0,0,0,989927,35),(54324,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 989927 has requested a time extension',0,'2020-01-12 13:18:20',0,0,0,989927,35),(54325,'Time extension accepted','System','username10','Your time extension for the request [id:989927] has been accepted!',0,'2020-01-12 13:18:34',0,0,0,-1,-1),(54326,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:18:51',0,0,0,989928,36),(54327,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989928].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:18:56',0,0,0,989928,36),(54328,'A phase is pending on deadline confirmation','System','username10','The phase [36] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:19:15',0,0,0,989928,36),(54329,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [36] has been rejected!',0,'2020-01-12 13:19:26',0,0,0,989928,36),(54330,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:22:44',0,0,0,989929,37),(54331,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989929].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:22:51',0,0,0,989929,37),(54332,'A phase is pending on deadline confirmation','System','username10','The phase [37] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:23:08',0,0,0,989929,37),(54333,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [37] has been rejected!',0,'2020-01-12 13:23:16',0,0,0,989929,37),(54334,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 13:25:59',0,0,0,989930,38),(54335,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[989930].\nPlease choose the required time to evaluate the request',0,'2020-01-12 13:26:08',0,0,0,989930,38),(54336,'A phase is pending on deadline confirmation','System','username10','The phase [38] is waiting for a confirmation for the deadline!',0,'2020-01-12 13:26:15',0,0,0,989930,38),(54337,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [38] has been rejected!',0,'2020-01-12 13:26:21',0,0,0,989930,38),(54338,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:09:00',0,0,0,989931,39),(54339,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:11:44',0,0,0,1,1),(54340,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[1].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:12:01',0,0,0,1,1),(54341,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:12:07',0,0,0,1,1),(54342,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [1] has been rejected!',0,'2020-01-12 14:12:19',0,0,0,1,1),(54343,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:12:47',0,0,0,2,2),(54344,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[2].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:12:52',0,0,0,2,2),(54345,'A phase is pending on deadline confirmation','System','username10','The phase [2] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:13:40',0,0,0,2,2),(54346,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [2] has been rejected!',0,'2020-01-12 14:13:46',0,0,0,2,2),(54347,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:14:16',0,0,0,1,1),(54348,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[1].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:14:21',0,0,0,1,1),(54349,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:14:33',0,0,0,1,1),(54350,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:14:34',0,0,0,1,1),(54351,'Deadline Time Rejected','System','username10','The deadline that you choosed for phase [1] has been rejected!',0,'2020-01-12 14:15:14',0,0,0,1,1),(54352,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:15:23',0,0,0,1,1),(54353,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [1] has been accepted!',0,'2020-01-12 14:15:27',0,0,0,1,1),(54354,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 1 has requested a time extension',0,'2020-01-12 14:15:35',0,0,0,1,1),(54355,'Time extension rejected','System','username10','Your time extension for the request [id:1] has been rejected!',0,'2020-01-12 14:15:53',0,0,0,-1,-1),(54356,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:17:45',0,0,0,2,2),(54357,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[2].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:17:53',0,0,0,2,2),(54358,'A phase is pending on deadline confirmation','System','username10','The phase [2] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:18:00',0,0,0,2,2),(54359,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [2] has been accepted!',0,'2020-01-12 14:18:05',0,0,0,2,2),(54360,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 2 has requested a time extension',0,'2020-01-12 14:18:19',0,0,0,2,2),(54361,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:22:00',0,0,0,3,1),(54362,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[3].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:22:05',0,0,0,3,1),(54363,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:22:11',0,0,0,3,1),(54364,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [1] has been accepted!',0,'2020-01-12 14:22:14',0,0,0,3,1),(54365,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 3 has requested a time extension',0,'2020-01-12 14:22:21',0,0,0,3,1),(54366,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:24:33',0,0,0,4,1),(54367,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[4].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:24:38',0,0,0,4,1),(54368,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:24:42',0,0,0,4,1),(54369,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [1] has been accepted!',0,'2020-01-12 14:24:47',0,0,0,4,1),(54370,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 4 has requested a time extension',0,'2020-01-12 14:24:54',0,0,0,4,1),(54371,'Time extension rejected','System','username10','Your time extension for the request [id:4] has been rejected!',0,'2020-01-12 14:25:15',0,0,0,-1,-1),(54372,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:25:37',0,0,0,5,2),(54373,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[5].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:25:42',0,0,0,5,2),(54374,'A phase is pending on deadline confirmation','System','username10','The phase [2] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:25:49',0,0,0,5,2),(54375,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [2] has been accepted!',0,'2020-01-12 14:25:53',0,0,0,5,2),(54376,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 5 has requested a time extension',0,'2020-01-12 14:26:04',0,0,0,5,2),(54377,'Time extension rejected','System','username10','Your time extension for the request [id:5] has been rejected!',0,'2020-01-12 14:26:13',0,0,0,-1,-1),(54378,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 14:30:23',0,0,0,6,1),(54379,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[6].\nPlease choose the required time to evaluate the request',0,'2020-01-12 14:30:28',0,0,0,6,1),(54380,'A phase is pending on deadline confirmation','System','username10','The phase [1] is waiting for a confirmation for the deadline!',0,'2020-01-12 14:30:32',0,0,0,6,1),(54381,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [1] has been accepted!',0,'2020-01-12 14:30:37',0,0,0,6,1),(54382,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 6 has requested a time extension',0,'2020-01-12 14:30:44',0,0,0,6,1),(54383,'Time extension rejected','System','username10','Your time extension for the request [id:6] has been rejected!',0,'2020-01-12 14:31:18',0,0,0,-1,-1),(54384,'Confirm time extension','System','username10','The evaluator FN10 LN10 of request 6 has requested a time extension',0,'2020-01-12 14:31:28',0,0,0,6,1),(54385,'Time extension rejected','System','username10','Your time extension for the request [id:6] has been rejected!',0,'2020-01-12 14:31:34',0,0,0,-1,-1),(54386,'A request is waiting for decision','System','username10','The requests [6] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 14:43:28',0,0,0,6,2),(54387,'A request is waiting for decision','System','username21','The requests [6] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 14:43:28',0,0,0,6,2),(54388,'A request is waiting for decision','System','username22','The requests [6] is waiting for a decision. you have 7 days to decide!',0,'2020-01-12 14:43:28',0,0,0,6,2),(54389,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-12 16:04:06',0,0,0,7,3),(54390,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[7].\nPlease choose the required time to evaluate the request',0,'2020-01-12 16:04:22',0,0,0,7,3),(54391,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-13 14:29:04',0,0,0,8,4),(54392,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[8].\nPlease choose the required time to evaluate the request',0,'2020-01-13 14:29:41',0,0,0,8,4),(54393,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-13 14:44:14',0,0,0,9,5),(54394,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[9].\nPlease choose the required time to evaluate the request',0,'2020-01-13 14:44:26',0,0,0,9,5),(54395,'A phase is pending on deadline confirmation','System','username10','The phase [5] is waiting for a confirmation for the deadline!',0,'2020-01-13 14:44:34',0,0,0,9,5),(54396,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [5] has been accepted!',0,'2020-01-13 14:44:39',0,0,0,9,5),(54397,'A request is waiting for decision','System','username10','The requests [9] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:44:50',0,0,0,9,6),(54398,'A request is waiting for decision','System','username21','The requests [9] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:44:50',0,0,0,9,6),(54399,'A request is waiting for decision','System','username22','The requests [9] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:44:50',0,0,0,9,6),(54400,'A phase is pending on deadline confirmation','System','username10','The phase [4] is waiting for a confirmation for the deadline!',0,'2020-01-13 14:47:17',0,0,0,8,4),(54401,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [4] has been accepted!',0,'2020-01-13 14:47:28',0,0,0,8,4),(54402,'A request is waiting for decision','System','username10','The requests [8] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:47:40',0,0,0,8,7),(54403,'A request is waiting for decision','System','username21','The requests [8] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:47:40',0,0,0,8,7),(54404,'A request is waiting for decision','System','username22','The requests [8] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:47:40',0,0,0,8,7),(54405,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-13 14:52:46',0,0,0,10,8),(54406,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[10].\nPlease choose the required time to evaluate the request',0,'2020-01-13 14:52:55',0,0,0,10,8),(54407,'A phase is pending on deadline confirmation','System','username10','The phase [8] is waiting for a confirmation for the deadline!',0,'2020-01-13 14:53:01',0,0,0,10,8),(54408,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [8] has been accepted!',0,'2020-01-13 14:53:23',0,0,0,10,8),(54409,'A request is waiting for decision','System','username10','The requests [10] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:53:38',0,0,0,10,9),(54410,'A request is waiting for decision','System','username21','The requests [10] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:53:38',0,0,0,10,9),(54411,'A request is waiting for decision','System','username22','The requests [10] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 14:53:38',0,0,0,10,9),(54412,'Confirm time extension','System','username10','The evaluator FN22 LN22 of request 10 has requested a time extension\nReason: jjj',0,'2020-01-13 14:54:56',0,0,0,10,9),(54413,'Assign an evaluator','System','username10','Please confirm or assign an evalutor to the request',0,'2020-01-13 16:23:06',0,0,0,11,10),(54414,'Set required time to evaluate a request','System','username10','You have been assigned to evaluate the request[11].\nPlease choose the required time to evaluate the request',0,'2020-01-13 16:23:16',0,0,0,11,10),(54415,'A phase is pending on deadline confirmation','System','username10','The phase [10] is waiting for a confirmation for the deadline!',0,'2020-01-13 16:23:24',0,0,0,11,10),(54416,'Deadline Time accepted','System','username10','The deadline that you choosed for phase [10] has been accepted!',0,'2020-01-13 16:23:34',0,0,0,11,10),(54417,'A request is waiting for decision','System','username10','The requests [11] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 16:23:46',0,0,0,11,11),(54418,'A request is waiting for decision','System','username21','The requests [11] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 16:23:46',0,0,0,11,11),(54419,'A request is waiting for decision','System','username22','The requests [11] is waiting for a decision. you have 7 days to decide!',0,'2020-01-13 16:23:46',0,0,0,11,11);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phase`
--

DROP TABLE IF EXISTS `phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phase` (
  `phaseID` bigint(8) NOT NULL,
  `requestID` bigint(8) NOT NULL,
  `phaseName` varchar(256) NOT NULL,
  `status` varchar(256) NOT NULL,
  `empNumber` bigint(8) NOT NULL,
  `deadline` timestamp NOT NULL,
  `estimatedTimeOfCompletion` timestamp NOT NULL,
  `timeOfCompletion` timestamp NOT NULL,
  `startingDate` timestamp NOT NULL,
  `hasBeenTimeExtended` tinyint(1) NOT NULL,
  PRIMARY KEY (`phaseID`),
  KEY `requestIDPhaseFK` (`requestID`),
  KEY `employeeNumberOfPhase_idx` (`empNumber`),
  CONSTRAINT `employeeNumberOfPhase` FOREIGN KEY (`empNumber`) REFERENCES `employee` (`empNumber`),
  CONSTRAINT `requestIDPhaseFK` FOREIGN KEY (`requestID`) REFERENCES `changerequest` (`requestID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phase`
--

LOCK TABLES `phase` WRITE;
/*!40000 ALTER TABLE `phase` DISABLE KEYS */;
INSERT INTO `phase` VALUES (1,6,'Evaluation','Closed',10,'2020-01-29 01:30:00','2020-01-29 01:30:00','1999-01-01 02:30:00','2020-01-12 14:30:23',0),(2,6,'Evaluation','Closed',22,'2020-01-19 14:43:28','2020-01-19 14:43:28','1999-01-01 02:30:00','2020-01-12 14:43:28',0),(3,7,'Decision','Waiting To Set Time Required For Evaluation',10,'1999-01-01 02:30:00','1999-01-01 02:30:00','1999-01-01 02:30:00','2020-01-12 16:04:06',0),(4,8,'Evaluation','Closed',10,'2020-01-23 01:30:00','2020-01-23 01:30:00','1999-01-01 02:30:00','2020-01-13 14:29:03',0),(5,9,'Evaluation','Closed',10,'2020-01-21 01:30:00','2020-01-21 01:30:00','1999-01-01 02:30:00','2020-01-13 14:44:13',0),(6,9,'Decision','Active',22,'2020-01-20 14:44:50','2020-01-20 14:44:50','1999-01-01 02:30:00','2020-01-13 14:44:50',0),(7,8,'Decision','Active',22,'2020-01-20 14:47:39','2020-01-20 14:47:39','1999-01-01 02:30:00','2020-01-13 14:47:39',0),(8,10,'Evaluation','Closed',10,'2020-01-14 01:30:00','2020-01-14 01:30:00','1999-01-01 02:30:00','2020-01-13 14:52:45',0),(9,10,'Decision','Active And Waiting For Time Extension',22,'2020-01-20 14:53:37','2020-01-20 14:53:37','1999-01-01 02:30:00','2020-01-13 14:53:37',0),(10,11,'Evaluation','Closed',10,'2020-01-19 01:30:00','2020-01-19 01:30:00','1999-01-01 02:30:00','2020-01-13 16:23:05',0),(11,11,'Decision','Active',22,'2020-01-20 16:23:46','2020-01-20 16:23:46','1999-01-01 02:30:00','2020-01-13 16:23:46',0);
/*!40000 ALTER TABLE `phase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phasetimeextensionrequest`
--

DROP TABLE IF EXISTS `phasetimeextensionrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phasetimeextensionrequest` (
  `phaseID` bigint(8) NOT NULL,
  `requestedTimeInDays` int(11) NOT NULL DEFAULT '0',
  `requestedTimeInHours` int(11) NOT NULL DEFAULT '0',
  `description` varchar(256) NOT NULL,
  PRIMARY KEY (`phaseID`),
  CONSTRAINT `phaseIDPhaseTimeExtensionRequestFK` FOREIGN KEY (`phaseID`) REFERENCES `phase` (`phaseID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phasetimeextensionrequest`
--

LOCK TABLES `phasetimeextensionrequest` WRITE;
/*!40000 ALTER TABLE `phasetimeextensionrequest` DISABLE KEYS */;
INSERT INTO `phasetimeextensionrequest` VALUES (9,7,7,'jjj');
/*!40000 ALTER TABLE `phasetimeextensionrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `stuNumber` bigint(8) NOT NULL,
  `stuDepartment` varchar(256) NOT NULL,
  `userName` varchar(256) NOT NULL,
  PRIMARY KEY (`stuNumber`),
  KEY `userNameStudentFK` (`userName`),
  CONSTRAINT `userNameStudentFK` FOREIGN KEY (`userName`) REFERENCES `systemuser` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (10,'Software','username10'),(11,'Software','username11'),(12,'Software','username12'),(13,'Software','username13'),(14,'Software','username14'),(15,'Software','username15'),(16,'Software','username16'),(17,'Software','username17'),(18,'Software','username18'),(19,'Software','username19'),(20,'Software','username20'),(21,'Software','username21');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor`
--

DROP TABLE IF EXISTS `supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisor` (
  `empNumber` bigint(8) NOT NULL,
  PRIMARY KEY (`empNumber`),
  CONSTRAINT `SupervisorFK` FOREIGN KEY (`empNumber`) REFERENCES `employee` (`empNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor`
--

LOCK TABLES `supervisor` WRITE;
/*!40000 ALTER TABLE `supervisor` DISABLE KEYS */;
INSERT INTO `supervisor` VALUES (10);
/*!40000 ALTER TABLE `supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisorupdatecr`
--

DROP TABLE IF EXISTS `supervisorupdatecr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisorupdatecr` (
  `updateID` bigint(8) NOT NULL,
  `supervisorID` bigint(8) NOT NULL,
  `updateDescription` varchar(256) NOT NULL,
  `updateDate` timestamp NOT NULL,
  PRIMARY KEY (`updateID`),
  KEY `empNumberSupervisorUpdateCRFK` (`supervisorID`),
  CONSTRAINT `empNumberSupervisorUpdateCRFK` FOREIGN KEY (`supervisorID`) REFERENCES `employee` (`empNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisorupdatecr`
--

LOCK TABLES `supervisorupdatecr` WRITE;
/*!40000 ALTER TABLE `supervisorupdatecr` DISABLE KEYS */;
/*!40000 ALTER TABLE `supervisorupdatecr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `systemuser` (
  `userName` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `firstName` varchar(256) NOT NULL,
  `lastName` varchar(256) NOT NULL,
  `phoneNo` varchar(256) NOT NULL,
  `isOnline` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemuser`
--

LOCK TABLES `systemuser` WRITE;
/*!40000 ALTER TABLE `systemuser` DISABLE KEYS */;
INSERT INTO `systemuser` VALUES ('system','1','1','system','system','1',0),('username0','0001','0@gmail.com','FN0','LN0','052-2580',0),('username1','1001','1@gmail.com','FN1','LN1','052-2581',0),('username10','10001','10@gmail.com','FN10','LN10','052-25810',0),('username11','11001','11@gmail.com','FN11','LN11','052-25811',0),('username12','12001','12@gmail.com','FN12','LN12','052-25812',0),('username13','13001','13@gmail.com','FN13','LN13','052-25813',0),('username14','14001','14@gmail.com','FN14','LN14','052-25814',0),('username15','15001','15@gmail.com','FN15','LN15','052-25815',0),('username16','16001','16@gmail.com','FN16','LN16','052-25816',0),('username17','17001','17@gmail.com','FN17','LN17','052-25817',0),('username18','18001','18@gmail.com','FN18','LN18','052-25818',0),('username19','19001','19@gmail.com','FN19','LN19','052-25819',0),('username2','2001','2@gmail.com','FN2','LN2','052-2582',0),('username20','20001','20@gmail.com','FN20','LN20','052-25820',0),('username21','21001','21@gmail.com','FN21','LN21','052-25821',0),('username22','22001','22@gmail.com','FN22','LN22','052-25822',0),('username3','3001','3@gmail.com','FN3','LN3','052-2583',0),('username4','4001','4@gmail.com','FN4','LN4','052-2584',0),('username5','5001','5@gmail.com','FN5','LN5','052-2585',0),('username6','6001','6@gmail.com','FN6','LN6','052-2586',0),('username7','7001','7@gmail.com','FN7','LN7','052-2587',0),('username8','8001','8@gmail.com','FN8','LN8','052-2588',0),('username9','9001','9@gmail.com','FN9','LN9','052-2589',0);
/*!40000 ALTER TABLE `systemuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'icm'
--

--
-- Dumping routines for database 'icm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-14  8:27:04
