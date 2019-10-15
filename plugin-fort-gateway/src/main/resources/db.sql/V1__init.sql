-- MySQL dump 10.16  Distrib 10.2.7-MariaDB, for osx10.12 (x86_64)
--
-- Host: localhost    Database: dliyun_platform
-- ------------------------------------------------------
-- Server version	10.2.7-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `fort_gateway_host_auth`
--

DROP TABLE IF EXISTS `fort_gateway_host_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fort_gateway_host_auth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `auth_type` int(1) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `auth_text` text NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fort_gateway_host_auth`
--

LOCK TABLES `fort_gateway_host_auth` WRITE;
/*!40000 ALTER TABLE `fort_gateway_host_auth` DISABLE KEYS */;
/*!40000 ALTER TABLE `fort_gateway_host_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fort_gateway_host_group`
--

DROP TABLE IF EXISTS `fort_gateway_host_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fort_gateway_host_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fort_gateway_host_group`
--

LOCK TABLES `fort_gateway_host_group` WRITE;
/*!40000 ALTER TABLE `fort_gateway_host_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `fort_gateway_host_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fort_gateway_host_group_users`
--

DROP TABLE IF EXISTS `fort_gateway_host_group_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fort_gateway_host_group_users` (
  `id` varchar(32) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id_index` (`group_id`) USING BTREE,
  KEY `uid_index` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fort_gateway_host_group_users`
--

LOCK TABLES `fort_gateway_host_group_users` WRITE;
/*!40000 ALTER TABLE `fort_gateway_host_group_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `fort_gateway_host_group_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fort_gateway_host_info`
--

DROP TABLE IF EXISTS `fort_gateway_host_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fort_gateway_host_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL,
  `auth_id` bigint(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `host_address` varchar(45) NOT NULL,
  `port_number` int(11) NOT NULL,
  `os` varchar(45) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fort_gateway_host_info`
--

LOCK TABLES `fort_gateway_host_info` WRITE;
/*!40000 ALTER TABLE `fort_gateway_host_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `fort_gateway_host_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-27 13:48:13
