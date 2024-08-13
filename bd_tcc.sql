-- MySQL dump 10.13  Distrib 8.3.0, for macos14.2 (arm64)
--
-- Host: localhost    Database: bd_tcc2
-- ------------------------------------------------------
-- Server version	8.3.0

create database bd_tcc;
use bd_tcc;


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
    `notification_id` INT NOT NULL AUTO_INCREMENT,
    `advisor_id` INT DEFAULT NULL,
    `notification_status` ENUM('READ', 'NOT_READ') DEFAULT 'NOT_READ',
    `body` TEXT,
    PRIMARY KEY (`notification_id`),
    UNIQUE KEY `notification_id` (`notification_id`),
    KEY `advisor_id` (`advisor_id`),
    CONSTRAINT `advisornotifications_ibfk_1` FOREIGN KEY (`advisor_id`)
        REFERENCES `user_advisor` (`advisor_id`)
)  ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
    `document_id` INT NOT NULL AUTO_INCREMENT,
    `document_path` VARCHAR(80) NOT NULL,
    `document_title` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`document_id`)
)  ENGINE=INNODB AUTO_INCREMENT=26 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
    `feedback_id` INT NOT NULL AUTO_INCREMENT,
    `feedback_date` DATE NOT NULL,
    `body` TEXT,
    `task_id` INT DEFAULT NULL,
    PRIMARY KEY (`feedback_id`),
    KEY `task_id` (`task_id`),
    CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`task_id`)
        REFERENCES `task` (`task_id`)
)  ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `GuidingNotifications`
--

DROP TABLE IF EXISTS `GuidingNotifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GuidingNotifications` (
    `notification_id` INT NOT NULL AUTO_INCREMENT,
    `guiding_id` INT DEFAULT NULL,
    `notification_status` ENUM('READ', 'NOT_READ') DEFAULT 'NOT_READ',
    `body` TEXT,
    PRIMARY KEY (`notification_id`),
    UNIQUE KEY `notification_id` (`notification_id`),
    KEY `guiding_id` (`guiding_id`),
    CONSTRAINT `guidingnotifications_ibfk_1` FOREIGN KEY (`guiding_id`)
        REFERENCES `user_guiding` (`guiding_id`)
)  ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `project_tcc`
--

DROP TABLE IF EXISTS `project_tcc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_tcc` (
    `project_id` INT NOT NULL AUTO_INCREMENT,
    `project_name` VARCHAR(45) DEFAULT NULL,
    `project_status` ENUM('INICIADO', 'EM_PROGRESSO', 'FINALIZADO') DEFAULT 'INICIADO',
    `project_grade` DOUBLE DEFAULT NULL,
    PRIMARY KEY (`project_id`),
    UNIQUE KEY `project_id` (`project_id`),
    UNIQUE KEY `project_name` (`project_name`)
)  ENGINE=INNODB AUTO_INCREMENT=33 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `relation_project_advisor`
--

DROP TABLE IF EXISTS `relation_project_advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation_project_advisor` (
    `project_id` INT DEFAULT NULL,
    `advisor_id` INT DEFAULT NULL,
    KEY `project_id` (`project_id`),
    KEY `advisor_id` (`advisor_id`),
    CONSTRAINT `relation_project_advisor_ibfk_1` FOREIGN KEY (`project_id`)
        REFERENCES `project_tcc` (`project_id`),
    CONSTRAINT `relation_project_advisor_ibfk_2` FOREIGN KEY (`advisor_id`)
        REFERENCES `user_advisor` (`advisor_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `relation_project_user_guiding`
--

DROP TABLE IF EXISTS `relation_project_user_guiding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation_project_user_guiding` (
    `project_id` INT DEFAULT NULL,
    `guiding_id` INT DEFAULT NULL,
    UNIQUE KEY `guiding_id` (`guiding_id`),
    KEY `project_id` (`project_id`),
    CONSTRAINT `relation_project_user_guiding_ibfk_1` FOREIGN KEY (`project_id`)
        REFERENCES `project_tcc` (`project_id`),
    CONSTRAINT `relation_project_user_guiding_ibfk_2` FOREIGN KEY (`guiding_id`)
        REFERENCES `user_guiding` (`guiding_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `sub_feedback`
--

DROP TABLE IF EXISTS `sub_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_feedback` (
    `sub_feedback_id` INT NOT NULL AUTO_INCREMENT,
    `feedback_date` DATE NOT NULL,
    `body` VARCHAR(200) DEFAULT NULL,
    `subtask_id` INT DEFAULT NULL,
    PRIMARY KEY (`sub_feedback_id`),
    KEY `subtask_id` (`subtask_id`),
    CONSTRAINT `sub_feedback_ibfk_2` FOREIGN KEY (`subtask_id`)
        REFERENCES `subtask` (`subtask_id`)
)  ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `subtask`
--

DROP TABLE IF EXISTS `subtask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subtask` (
    `subtask_id` INT NOT NULL AUTO_INCREMENT,
    `task_id` INT NOT NULL,
    `initial_date` DATETIME NOT NULL,
    `final_date` DATETIME NOT NULL,
    `title` VARCHAR(45) NOT NULL,
    `task_description` VARCHAR(200) DEFAULT NULL,
    `task_status` ENUM('INCOMPLETA', 'COMPLETA') DEFAULT 'INCOMPLETA',
    `task_grade` DOUBLE DEFAULT NULL,
    `document_id` INT DEFAULT NULL,
    PRIMARY KEY (`subtask_id`),
    KEY `task_id` (`task_id`),
    KEY `fk_document1` (`document_id`),
    CONSTRAINT `fk_document1` FOREIGN KEY (`document_id`)
        REFERENCES `documents` (`document_id`),
    CONSTRAINT `subtask_ibfk_1` FOREIGN KEY (`task_id`)
        REFERENCES `task` (`task_id`)
)  ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
    `task_id` INT NOT NULL AUTO_INCREMENT,
    `initial_date` DATETIME NOT NULL,
    `final_date` DATETIME NOT NULL,
    `title` VARCHAR(45) NOT NULL,
    `task_description` VARCHAR(200) DEFAULT NULL,
    `task_status` ENUM('INCOMPLETA', 'COMPLETA') DEFAULT 'INCOMPLETA',
    `project_id` INT DEFAULT NULL,
    `task_grade` DOUBLE DEFAULT NULL,
    `document_id` INT DEFAULT NULL,
    PRIMARY KEY (`task_id`),
    KEY `project_id` (`project_id`),
    KEY `fk_document` (`document_id`),
    CONSTRAINT `fk_document` FOREIGN KEY (`document_id`)
        REFERENCES `documents` (`document_id`),
    CONSTRAINT `task_ibfk_1` FOREIGN KEY (`project_id`)
        REFERENCES `project_tcc` (`project_id`)
)  ENGINE=INNODB AUTO_INCREMENT=17 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `user_advisor`
--

DROP TABLE IF EXISTS `user_advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_advisor` (
    `advisor_id` INT NOT NULL,
    `username` VARCHAR(45) NOT NULL,
    `lastname` VARCHAR(45) DEFAULT NULL,
    `user_level` INT DEFAULT NULL,
    `user_password` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`advisor_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_advisor`
--

LOCK TABLES `user_advisor` WRITE;
/*!40000 ALTER TABLE `user_advisor` DISABLE KEYS */;
INSERT INTO `user_advisor` VALUES (1,'ALEXANDRA','APARECIDA',1,'alexandra','alexandra@email.com');
/*!40000 ALTER TABLE `user_advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_guiding`
--

DROP TABLE IF EXISTS `user_guiding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_guiding` (
    `guiding_id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `lastname` VARCHAR(45) DEFAULT NULL,
    `user_level` INT DEFAULT NULL,
    `user_password` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`guiding_id`)
)  ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=UTF8MB4 COLLATE = UTF8MB4_0900_AI_CI;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_guiding`
--

LOCK TABLES `user_guiding` WRITE;
/*!40000 ALTER TABLE `user_guiding` DISABLE KEYS */;
INSERT INTO `user_guiding` VALUES (1,'Eytor','Lima',1,'eytor','eytor@email.com'),(2,'Pedro','Clemonini',1,'pedro','pedro@email.com');
/*!40000 ALTER TABLE `user_guiding` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bd_tcc2'
--
/*!50003 DROP PROCEDURE IF EXISTS `addFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addFeedback`(
   IN feedback_datex DATETIME,
   IN bodyx TEXT,
   IN task_idx INT
)
BEGIN
    DECLARE search_id INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erro ao criar o projeto.';
    END;

    START TRANSACTION;

 
    INSERT INTO feedback(feedback_date, body, task_id) 
    VALUES (feedback_datex, bodyx, task_idx);

 
    SELECT project_id INTO search_id 
    FROM task 
    WHERE task_id = task_idx;


    IF search_id IS NOT NULL THEN

        SELECT guiding_Id INTO search_id 
        FROM relation_project_user_guiding 
        WHERE project_id = search_id;


        IF search_id IS NOT NULL THEN
            INSERT INTO GuidingNotifications(guiding_id, body) 
            VALUES (search_id, bodyx);
        END IF;
    END IF;

    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `addSubtaskFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `addSubtaskFeedback`(
   IN feedback_datex DATETIME,
   IN bodyx TEXT,
   IN subtask_idx INT
)
BEGIN
    DECLARE task_id INT;
    DECLARE project_id INT;
    DECLARE guiding_id INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erro ao adicionar feedback para a subtarefa.';
    END;

    START TRANSACTION;

    INSERT INTO sub_feedback(feedback_date, body, subtask_id) 
    VALUES (feedback_datex, bodyx, subtask_idx);


    SELECT task_id INTO task_id 
    FROM subtask 
    WHERE subtask_id = subtask_idx;


    IF task_id IS NOT NULL THEN

        SELECT project_id INTO project_id 
        FROM task 
        WHERE task_id = task_id;


        IF project_id IS NOT NULL THEN

            SELECT guiding_Id INTO guiding_id 
            FROM relation_project_user_guiding 
            WHERE project_id = project_id;

 
            IF guiding_id IS NOT NULL THEN
                INSERT INTO GuidingNotifications(guiding_id, body) 
                VALUES (guiding_id, bodyx);
            END IF;
        END IF;
    END IF;

    COMMIT;
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

-- Dump completed on 2024-08-13 11:10:42
