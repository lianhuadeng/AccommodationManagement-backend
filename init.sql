-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: accommodation
-- ------------------------------------------------------
-- Server version	8.0.40

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
  `application_id` bigint NOT NULL AUTO_INCREMENT COMMENT '住宿调整申请id',
  `student_id` bigint DEFAULT NULL COMMENT '申请人学号',
  `leader_id` bigint DEFAULT NULL COMMENT '审核领导id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '处理者宿舍管理员id',
  `application_type` enum('普通入住','普通调整','学生互换','个人退宿','校外住宿') NOT NULL COMMENT '申请类型',
  `target_park` bigint DEFAULT NULL COMMENT '目标园区',
  `target_building` bigint DEFAULT NULL COMMENT '目标楼栋',
  `target_room` bigint DEFAULT NULL COMMENT '目标房间',
  `target_bed` bigint DEFAULT NULL COMMENT '目标床位',
  `status` enum('待审核','待处理','已处理') NOT NULL DEFAULT '待审核' COMMENT '申请状态',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`application_id`),
  KEY `application_user_user_id_fk` (`student_id`),
  KEY `application_user_user_id_fk_2` (`leader_id`),
  KEY `application_user_user_id_fk_3` (`dormitory_id`),
  KEY `application_building_building_id_fk` (`target_building`),
  KEY `application_park_park_id_fk` (`target_park`),
  KEY `application_room_room_id_fk` (`target_room`),
  KEY `application_bed_bed_id_fk` (`target_bed`),
  CONSTRAINT `application_bed_bed_id_fk` FOREIGN KEY (`target_bed`) REFERENCES `bed` (`bed_id`),
  CONSTRAINT `application_building_building_id_fk` FOREIGN KEY (`target_building`) REFERENCES `building` (`building_id`),
  CONSTRAINT `application_park_park_id_fk` FOREIGN KEY (`target_park`) REFERENCES `park` (`park_id`),
  CONSTRAINT `application_room_room_id_fk` FOREIGN KEY (`target_room`) REFERENCES `room` (`room_id`),
  CONSTRAINT `application_user_user_id_fk` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `application_user_user_id_fk_2` FOREIGN KEY (`leader_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `application_user_user_id_fk_3` FOREIGN KEY (`dormitory_id`) REFERENCES `user` (`user_id`)
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
  UNIQUE KEY `bed_pk` (`user_id`),
  KEY `bed_room_room_id_fk` (`room_id`),
  KEY `bed_user_user_id_fk` (`user_id`),
  CONSTRAINT `bed_room_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `bed_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
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
  `building_id` bigint NOT NULL AUTO_INCREMENT COMMENT '楼栋id',
  `park_id` bigint DEFAULT NULL COMMENT '园区id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿管工号',
  `floor_num` int DEFAULT NULL COMMENT '楼栋楼层数',
  `room_num` int DEFAULT NULL COMMENT '每层房间数',
  PRIMARY KEY (`building_id`),
  KEY `building_park_park_id_fk` (`park_id`),
  KEY `building_user_user_id_fk` (`dormitory_id`),
  CONSTRAINT `building_park_park_id_fk` FOREIGN KEY (`park_id`) REFERENCES `park` (`park_id`),
  CONSTRAINT `building_user_user_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `user` (`user_id`)
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
  `disciplinary_id` bigint NOT NULL AUTO_INCREMENT COMMENT '违纪id',
  `leader_id` bigint DEFAULT NULL COMMENT '扣分领导id',
  `student_id` bigint DEFAULT NULL COMMENT '学号',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍管理员id',
  `reason` varchar(200) DEFAULT NULL COMMENT '违纪原因',
  `score` bigint DEFAULT NULL COMMENT '处罚分数',
  PRIMARY KEY (`disciplinary_id`),
  KEY `disciplinary_user_user_id_fk` (`leader_id`),
  KEY `disciplinary_user_user_id_fk_2` (`student_id`),
  KEY `disciplinary_user_user_id_fk_3` (`dormitory_id`),
  CONSTRAINT `disciplinary_user_user_id_fk` FOREIGN KEY (`leader_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `disciplinary_user_user_id_fk_2` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `disciplinary_user_user_id_fk_3` FOREIGN KEY (`dormitory_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='违纪记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplinary`
--


--
-- Table structure for table `hygiene_check`
--

DROP TABLE IF EXISTS `hygiene_check`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hygiene_check` (
  `hygiene_id` bigint NOT NULL AUTO_INCREMENT COMMENT '卫生检查id',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍管理员工号',
  `room_id` bigint DEFAULT NULL COMMENT '房间号',
  `score` bigint DEFAULT NULL COMMENT '分数',
  `check_time` datetime DEFAULT NULL COMMENT '检查时间',
  PRIMARY KEY (`hygiene_id`),
  KEY `hygiene_check_room_room_id_fk` (`room_id`),
  KEY `hygiene_check_user_user_id_fk` (`dormitory_id`),
  CONSTRAINT `hygiene_check_room_room_id_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `hygiene_check_user_user_id_fk` FOREIGN KEY (`dormitory_id`) REFERENCES `user` (`user_id`)
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
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `park_id` bigint NOT NULL AUTO_INCREMENT COMMENT '园区id',
  `name` varchar(100) DEFAULT NULL COMMENT '园区名',
  `type` enum('教师公寓','男生公寓','女生公寓') DEFAULT NULL COMMENT '园区类型',
  `building_num` bigint DEFAULT NULL COMMENT '园区内楼栋数量',
  PRIMARY KEY (`park_id`)
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
  `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`repair_id`),
  KEY `repair_user_user_id_fk` (`student_id`),
  KEY `repair_user_user_id_fk_2` (`dormitory_id`),
  KEY `repair_user_user_id_fk_3` (`maintenance_id`),
  CONSTRAINT `repair_user_user_id_fk` FOREIGN KEY (`student_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `repair_user_user_id_fk_2` FOREIGN KEY (`dormitory_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `repair_user_user_id_fk_3` FOREIGN KEY (`maintenance_id`) REFERENCES `user` (`user_id`)
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
  `bed_num` int DEFAULT NULL COMMENT '房间床位数',
  PRIMARY KEY (`room_id`),
  KEY `room_building_building_id_fk` (`building_id`),
  CONSTRAINT `room_building_building_id_fk` FOREIGN KEY (`building_id`) REFERENCES `building` (`building_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房间';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL COMMENT '学号/工号',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `college` varchar(50) DEFAULT NULL COMMENT '学院',
  `major` varchar(50) DEFAULT NULL COMMENT '专业',
  `grade` bigint DEFAULT NULL COMMENT '年级',
  `clazz` bigint DEFAULT NULL COMMENT '班级',
  `sex` enum('男','女') DEFAULT NULL COMMENT '性别',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `type` enum('学生','教师','系统管理员','宿舍管理员','维修管理员','分管领导') DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `password`, `college`, `major`, `grade`, `clazz`, `sex`, `contact`, `type`) VALUES (2022141460307,'林浩东','9bf1531c742724ddbb85f44de35f0264','计算机学院','计科',3,6,NULL,'12345678901','学生'),(2022141460311,'丘俊杰','aa8d62b2587ff43a6ac1acebc2eebfdc','计算机学院','计科',3,6,NULL,'12345678901','学生');
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-24 15:17:03
