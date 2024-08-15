-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: localhost    Database: bd_tcc
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AdvisorNotifications`
--

DROP TABLE IF EXISTS `AdvisorNotifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AdvisorNotifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `advisor_id` int DEFAULT NULL,
  `notification_status` enum('LIDA','NAO_LIDA') DEFAULT 'NAO_LIDA',
  `body` text,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id` (`notification_id`),
  KEY `advisor_id` (`advisor_id`),
  CONSTRAINT `advisornotifications_ibfk_1` FOREIGN KEY (`advisor_id`) REFERENCES `user_advisor` (`advisor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `document_id` int NOT NULL AUTO_INCREMENT,
  `document_path` varchar(80) NOT NULL,
  `document_title` varchar(50) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `feedback_id` int NOT NULL AUTO_INCREMENT,
  `feedback_date` date NOT NULL,
  `body` text,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `GuidingNotifications`
--

DROP TABLE IF EXISTS `GuidingNotifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GuidingNotifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `guiding_id` int DEFAULT NULL,
  `notification_status` enum('READ','NOT_READ') DEFAULT 'NOT_READ',
  `body` text,
  PRIMARY KEY (`notification_id`),
  UNIQUE KEY `notification_id` (`notification_id`),
  KEY `guiding_id` (`guiding_id`),
  CONSTRAINT `guidingnotifications_ibfk_1` FOREIGN KEY (`guiding_id`) REFERENCES `user_guiding` (`guiding_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_tcc`
--

DROP TABLE IF EXISTS `project_tcc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_tcc` (
  `project_id` int NOT NULL AUTO_INCREMENT,
  `project_name` varchar(45) DEFAULT NULL,
  `project_status` enum('INICIADO','EM_PROGRESSO','FINALIZADO') DEFAULT 'INICIADO',
  `project_grade` double DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `project_id` (`project_id`),
  UNIQUE KEY `project_name` (`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relation_project_advisor`
--

DROP TABLE IF EXISTS `relation_project_advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation_project_advisor` (
  `project_id` int DEFAULT NULL,
  `advisor_id` int DEFAULT NULL,
  KEY `project_id` (`project_id`),
  KEY `advisor_id` (`advisor_id`),
  CONSTRAINT `relation_project_advisor_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_tcc` (`project_id`),
  CONSTRAINT `relation_project_advisor_ibfk_2` FOREIGN KEY (`advisor_id`) REFERENCES `user_advisor` (`advisor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relation_project_user_guiding`
--

DROP TABLE IF EXISTS `relation_project_user_guiding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation_project_user_guiding` (
  `project_id` int DEFAULT NULL,
  `guiding_id` int DEFAULT NULL,
  UNIQUE KEY `guiding_id` (`guiding_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `relation_project_user_guiding_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_tcc` (`project_id`),
  CONSTRAINT `relation_project_user_guiding_ibfk_2` FOREIGN KEY (`guiding_id`) REFERENCES `user_guiding` (`guiding_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subtask`
--

DROP TABLE IF EXISTS `subtask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subtask` (
  `subtask_id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `initial_date` datetime NOT NULL,
  `final_date` datetime NOT NULL,
  `title` varchar(45) NOT NULL,
  `task_description` varchar(200) DEFAULT NULL,
  `task_status` enum('INCOMPLETA','COMPLETA') DEFAULT 'INCOMPLETA',
  `task_grade` double DEFAULT NULL,
  `document_id` int DEFAULT NULL,
  `feedback_id` int DEFAULT NULL,
  PRIMARY KEY (`subtask_id`),
  KEY `fk_document1` (`document_id`),
  KEY `fk_feedback1_idx` (`feedback_id`),
  CONSTRAINT `fk_document1` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`),
  CONSTRAINT `fk_feedback1` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `initial_date` datetime NOT NULL,
  `final_date` datetime NOT NULL,
  `title` varchar(45) NOT NULL,
  `task_description` varchar(200) DEFAULT NULL,
  `task_status` enum('INCOMPLETA','COMPLETA') DEFAULT 'INCOMPLETA',
  `project_id` int DEFAULT NULL,
  `task_grade` double DEFAULT NULL,
  `document_id` int DEFAULT NULL,
  `feedback_id` int DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `project_id` (`project_id`),
  KEY `fk_document` (`document_id`),
  KEY `fk_feedback_idx` (`feedback_id`),
  CONSTRAINT `fk_document` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`),
  CONSTRAINT `fk_feedback` FOREIGN KEY (`feedback_id`) REFERENCES `feedback` (`feedback_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project_tcc` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_advisor`
--

DROP TABLE IF EXISTS `user_advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_advisor` (
  `advisor_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `user_level` int DEFAULT NULL,
  `user_password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`advisor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_guiding`
--

DROP TABLE IF EXISTS `user_guiding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_guiding` (
  `guiding_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `user_level` int DEFAULT NULL,
  `user_password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`guiding_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_advisor` WRITE;
/*!40000 ALTER TABLE `user_advisor` DISABLE KEYS */;
INSERT INTO `user_advisor` VALUES (1,'Alexandra','Aparecida',1,'alexandra','alexandra@email.com');
/*!40000 ALTER TABLE `user_advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_guiding`
--

LOCK TABLES `user_guiding` WRITE;
/*!40000 ALTER TABLE `user_guiding` DISABLE KEYS */;
INSERT INTO `user_guiding` VALUES (1,'Eytor','Lima',1,'eytor','eytor@email.com'),(2,'Pedro','Clemonini',1,'pedro','pedro@email.com');
/*!40000 ALTER TABLE `user_guiding` ENABLE KEYS */;
UNLOCK TABLES;





--
-- Dumping routines for database 'bd_tcc'
--
/*!50003 DROP PROCEDURE IF EXISTS `addSubTaskFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSubTaskFeedback`(IN feedback_datex DATE, IN feedback_bodyx TEXT, IN subtask_idx INT)
BEGIN
DECLARE s_feedback_id INT;
INSERT INTO feedback(feedback_date,body) values (feedback_datex,feedback_bodyx);

SELECT LAST_INSERT_ID() INTO s_feedback_id;

UPDATE subtask set feedback_id = s_feedback_id where subtask_id = subtask_idx;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addTaskFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addTaskFeedback`(IN feedback_datex DATE, IN feedback_bodyx TEXT, IN task_idx INT)
BEGIN
DECLARE s_feedback_id INT;
INSERT INTO feedback(feedback_date,body) values (feedback_datex,feedback_bodyx);

SELECT LAST_INSERT_ID() INTO s_feedback_id;

UPDATE task set feedback_id = s_feedback_id where task_id = task_idx;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getGuidingProjects` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getGuidingProjects`(IN guiding_idx INT)
BEGIN

SELECT project_tcc.project_id,advisor_id,guiding_id,project_name,project_status,project_grade
FROM relation_project_user_guiding
INNER JOIN project_tcc
ON project_tcc.project_id = relation_project_user_guiding.project_id
INNER JOIN relation_project_advisor
ON project_tcc.project_id = relation_project_advisor.project_id
WHERE guiding_id = guiding_idx;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getProjects` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getProjects`(IN advisor_idx INT)
BEGIN

SELECT project_tcc.project_id,advisor_id,guiding_id,project_name,project_status,project_grade
FROM relation_project_advisor
INNER JOIN project_tcc
ON project_tcc.project_id = relation_project_advisor.project_id
INNER JOIN relation_project_user_guiding
ON project_tcc.project_id = relation_project_user_guiding.project_id
WHERE advisor_id = advisor_idx;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `newProject` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `newProject`(
    IN project_namex VARCHAR(50),
    IN guiding_idx INT,
    IN advisor_idx INT
)
BEGIN
    DECLARE search_id INT;
    

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
	
        ROLLBACK;
      
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erro ao criar o projeto.';
    END;


    START TRANSACTION;


    INSERT INTO project_tcc (project_name, project_grade) 
    VALUES (project_namex, 0);


    SELECT project_id INTO search_id 
    FROM project_tcc 
    WHERE project_name = project_namex;


    INSERT INTO relation_project_advisor (project_id, advisor_id) 
    VALUES (search_id, advisor_idx);

    INSERT INTO relation_project_user_guiding (project_id, guiding_id) 
    VALUES (search_id, guiding_idx);

    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `taskDocumentInsert` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `taskDocumentInsert`(IN document_pathx varchar(80), iN document_titlex varchar(50), OUT p_generatedId INT)
BEGIN
INSERT INTO documents(document_path,document_title) values (document_pathx,document_titlex);
SET p_generatedId = LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-15 16:59:31
