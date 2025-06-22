-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: accommodation
-- ------------------------------------------------------
-- Server version	8.0.40
drop database if exists accommodation;
use accommodation;

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
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `application_id` bigint NOT NULL COMMENT '住宿调整申请id',
  `student_id` bigint DEFAULT NULL COMMENT '申请人学号',
  `leader_id` bigint DEFAULT NULL COMMENT '审核领导id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '处理者宿舍管理员id',
  `application_type` enum('普通入住','批量排宿','普通调整','学生互换','批量调换','个人退宿','批量退宿','校外住宿') NOT NULL COMMENT '申请类型',
  `content` varchar(100) DEFAULT NULL COMMENT '申请内容',
  `status` enum('待审核','待处理','已处理') NOT NULL DEFAULT '待审核' COMMENT '申请状态',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`application_id`),
  KEY `application_dormitory_admin_dormitory_id_fk` (`dormitory_id`),
  KEY `application_leader_leader_id_fk` (`leader_id`),
  KEY `application_student_student_id_fk` (`student_id`),
  CONSTRAINT `application_dormitory_admin_dormitory_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory_admin` (`dormitory_id`),
  CONSTRAINT `application_leader_leader_id_fk` FOREIGN KEY (`leader_id`) REFERENCES `leader` (`leader_id`),
  CONSTRAINT `application_student_student_id_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='住宿调整申请';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--


--
-- Table structure for table `bed`
--

DROP TABLE IF EXISTS `bed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bed` (
  `bed_id` bigint NOT NULL COMMENT '床位号',
  `room_id` bigint DEFAULT NULL COMMENT '房间号',
  `user_id` bigint DEFAULT NULL COMMENT '学号或工号',
  PRIMARY KEY (`bed_id`),
  KEY `bed_room_room_id_fk` (`room_id`),
  CONSTRAINT `bed_room_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='床位';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed`
--


--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `building` (
  `building_id` bigint NOT NULL COMMENT '楼栋id',
  `park_id` bigint DEFAULT NULL COMMENT '园区id',
  PRIMARY KEY (`building_id`),
  KEY `building_park_park_id_fk` (`park_id`),
  CONSTRAINT `building_park_park_id_fk` FOREIGN KEY (`park_id`) REFERENCES `park` (`park_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='楼栋';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--


--
-- Table structure for table `disciplinary`
--

DROP TABLE IF EXISTS `disciplinary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disciplinary` (
  `disciplinary_id` bigint NOT NULL COMMENT '违纪id',
  `leader_id` bigint DEFAULT NULL COMMENT '扣分领导id',
  `student_id` bigint DEFAULT NULL COMMENT '学号',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍管理员id',
  `reason` varchar(200) DEFAULT NULL COMMENT '违纪原因',
  `score` bigint DEFAULT NULL COMMENT '处罚分数',
  PRIMARY KEY (`disciplinary_id`),
  KEY `disciplinary_dormitory_admin_dormitory_id_fk` (`dormitory_id`),
  KEY `disciplinary_leader_leader_id_fk` (`leader_id`),
  KEY `disciplinary_student_student_id_fk` (`student_id`),
  CONSTRAINT `disciplinary_dormitory_admin_dormitory_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory_admin` (`dormitory_id`),
  CONSTRAINT `disciplinary_leader_leader_id_fk` FOREIGN KEY (`leader_id`) REFERENCES `leader` (`leader_id`),
  CONSTRAINT `disciplinary_student_student_id_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='违纪记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplinary`
--


--
-- Table structure for table `dormitory_admin`
--

DROP TABLE IF EXISTS `dormitory_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dormitory_admin` (
  `dormitory_id` bigint NOT NULL COMMENT '宿舍管理员工号',
  `park_id` bigint DEFAULT NULL COMMENT '园区id',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`dormitory_id`),
  KEY `dormitory_admin_park_park_id_fk` (`park_id`),
  CONSTRAINT `dormitory_admin_park_park_id_fk` FOREIGN KEY (`park_id`) REFERENCES `park` (`park_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormitory_admin`
--


--
-- Table structure for table `hygiene_check`
--

DROP TABLE IF EXISTS `hygiene_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hygiene_check` (
  `hygiene_id` bigint NOT NULL COMMENT '卫生检查id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍管理员工号',
  `room_id` bigint DEFAULT NULL COMMENT '房间号',
  `score` bigint DEFAULT NULL COMMENT '分数',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  PRIMARY KEY (`hygiene_id`),
  KEY `hygiene_check_dormitory_admin_dormitory_id_fk` (`dormitory_id`),
  KEY `hygiene_check_room_room_id_fk` (`room_id`),
  CONSTRAINT `hygiene_check_dormitory_admin_dormitory_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory_admin` (`dormitory_id`),
  CONSTRAINT `hygiene_check_room_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='卫生检查';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hygiene_check`
--


--
-- Table structure for table `leader`
--

DROP TABLE IF EXISTS `leader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leader` (
  `leader_id` bigint NOT NULL COMMENT '分管领导工号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`leader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分管领导';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leader`
--


--
-- Table structure for table `maintenance_admin`
--

DROP TABLE IF EXISTS `maintenance_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance_admin` (
  `maintenance_admin` bigint NOT NULL COMMENT '维修管理员id',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`maintenance_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='维修管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance_admin`
--


--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `park_id` bigint NOT NULL COMMENT '园区id',
  `dormitory_id` bigint NOT NULL COMMENT '宿舍管理员工号',
  `name` varchar(100) NOT NULL COMMENT '园区名',
  `type` enum('teacher','boy','girl') NOT NULL COMMENT '园区类型',
  PRIMARY KEY (`park_id`),
  KEY `park_dormitory_admin_dormitory_id_fk` (`dormitory_id`),
  CONSTRAINT `park_dormitory_admin_dormitory_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory_admin` (`dormitory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='园区';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--


--
-- Table structure for table `repair`
--

DROP TABLE IF EXISTS `repair`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repair` (
  `repair_id` bigint NOT NULL AUTO_INCREMENT COMMENT '维修申请id',
  `student_id` bigint DEFAULT NULL COMMENT '申请人学生id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍管理员id',
  `maintenance_id` bigint DEFAULT NULL COMMENT '维修管理员id',
  `location` varchar(100) DEFAULT NULL COMMENT '位置',
  `content` varchar(100) DEFAULT NULL COMMENT '报修内容',
  `applicate_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`repair_id`),
  KEY `repair_dormitory_admin_dormitory_id_fk` (`dormitory_id`),
  KEY `repair_student_student_id_fk` (`student_id`),
  KEY `repair_maintenance_admin_maintenance_admin_fk` (`maintenance_id`),
  CONSTRAINT `repair_dormitory_admin_dormitory_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory_admin` (`dormitory_id`),
  CONSTRAINT `repair_maintenance_admin_maintenance_admin_fk` FOREIGN KEY (`maintenance_id`) REFERENCES `maintenance_admin` (`maintenance_admin`),
  CONSTRAINT `repair_student_student_id_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='维修申请';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repair`
--


--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` bigint NOT NULL COMMENT '房间id',
  `building_id` bigint DEFAULT NULL COMMENT '楼栋id',
  `floor` int DEFAULT NULL COMMENT '楼层',
  PRIMARY KEY (`room_id`),
  KEY `room_building_building_id_fk` (`building_id`),
  CONSTRAINT `room_building_building_id_fk` FOREIGN KEY (`building_id`) REFERENCES `building` (`building_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房间';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--


--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` bigint NOT NULL COMMENT '学生id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `college` varchar(50) DEFAULT NULL COMMENT '学院',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  `grade` bigint DEFAULT NULL COMMENT '年级',
  `clazz` bigint DEFAULT NULL COMMENT '班级',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--


--
-- Table structure for table `system_admin`
--

DROP TABLE IF EXISTS `system_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_admin` (
  `system_id` bigint NOT NULL COMMENT '工号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_admin`
--


--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` bigint NOT NULL COMMENT '教师工号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `college` varchar(50) DEFAULT NULL COMMENT '学院',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-22 10:55:46
