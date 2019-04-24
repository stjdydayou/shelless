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
-- Table structure for table `system_config`
--

DROP TABLE IF EXISTS `system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_config` (
  `id` varchar(32) NOT NULL,
  `data_value` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_config`
--

LOCK TABLES `system_config` WRITE;
/*!40000 ALTER TABLE `system_config` DISABLE KEYS */;
INSERT INTO `system_config` VALUES ('025fdcde303bcd4b375640f223dd4787','123456');
/*!40000 ALTER TABLE `system_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_role_authorities`
--

DROP TABLE IF EXISTS `system_oauth_role_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_role_authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(100) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8mb4 COMMENT='管理平台角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_role_authorities`
--

LOCK TABLES `system_oauth_role_authorities` WRITE;
/*!40000 ALTER TABLE `system_oauth_role_authorities` DISABLE KEYS */;
INSERT INTO `system_oauth_role_authorities` VALUES (375,'system.oauth.user.find',1000),(376,'system.oauth.user.add',1000),(377,'system.oauth.user.enable',1000),(378,'system.oauth.user.disable',1000),(379,'system.oauth.user.resetPassword',1000),(380,'system.oauth.user.roles',1000),(381,'system.oauth.role.find',1000),(382,'system.oauth.role.add',1000),(383,'system.oauth.role.edit',1000),(384,'system.oauth.role.delete',1000),(385,'system.oauth.role.authority',1000),(386,'system.setting.config.find',1000),(387,'system.setting.config.edit',1000);
/*!40000 ALTER TABLE `system_oauth_role_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_role_info`
--

DROP TABLE IF EXISTS `system_oauth_role_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_role_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8mb4 COMMENT='管理平台角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_role_info`
--

LOCK TABLES `system_oauth_role_info` WRITE;
/*!40000 ALTER TABLE `system_oauth_role_info` DISABLE KEYS */;
INSERT INTO `system_oauth_role_info` VALUES (1000,'超级管理员','管理网站一切事务');
/*!40000 ALTER TABLE `system_oauth_role_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_user_base_info`
--

DROP TABLE IF EXISTS `system_oauth_user_base_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_user_base_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(200) NOT NULL DEFAULT '' COMMENT '用户头像',
  `gender` int(1) NOT NULL DEFAULT 0,
  `state` tinyint(1) NOT NULL COMMENT '用户状态',
  `register_time` datetime NOT NULL,
  `register_ip` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9813 DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_base_info`
--

LOCK TABLES `system_oauth_user_base_info` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_base_info` DISABLE KEYS */;
INSERT INTO `system_oauth_user_base_info` VALUES (1000,'管理员','http://static.shuci.net/vk/images/defultAvatar.jpg',1,1,'2017-06-05 22:32:40','115.231.223.228'),(9812,'admin1','http://static.shuci.net/vk/images/defultAvatar.jpg',0,1,'2019-04-23 22:22:43','127.0.0.1');
/*!40000 ALTER TABLE `system_oauth_user_base_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_user_login_account`
--

DROP TABLE IF EXISTS `system_oauth_user_login_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_user_login_account` (
  `id` varchar(32) NOT NULL,
  `uid` bigint(20) NOT NULL COMMENT '平台uid',
  `login_account` varchar(50) NOT NULL COMMENT '登录账号\n微信登录保存的为unionid',
  `account_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '账号类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_account_type_unique` (`uid`,`account_type`),
  UNIQUE KEY `login_account_account_type_unique` (`login_account`,`account_type`),
  KEY `uid_index` (`uid`),
  KEY `login_account_index` (`login_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_login_account`
--

LOCK TABLES `system_oauth_user_login_account` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_login_account` DISABLE KEYS */;
INSERT INTO `system_oauth_user_login_account` VALUES ('03c5d1de6ed0290519cb0817092d619b',9812,'18073113111',2),('096930e16cc63b7b96f98fc9e8bf9f4a',9812,'stjdydayou@163.com',1),('10000',1000,'admin',0),('e68620e9468ee58b459a054f98fc625d',9812,'admin1',0);
/*!40000 ALTER TABLE `system_oauth_user_login_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_user_login_log`
--

DROP TABLE IF EXISTS `system_oauth_user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_user_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `login_ip` varchar(20) NOT NULL,
  `country` varchar(50) DEFAULT '' COMMENT '国家编号',
  `country_id` varchar(50) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `area_id` varchar(50) DEFAULT NULL,
  `region` varchar(50) DEFAULT NULL,
  `region_id` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `city_id` varchar(50) DEFAULT NULL,
  `county` varchar(50) DEFAULT NULL,
  `county_id` varchar(50) DEFAULT NULL,
  `isp` varchar(50) DEFAULT NULL,
  `isp_id` varchar(255) DEFAULT NULL,
  `login_time` datetime NOT NULL,
  `source` tinyint(1) DEFAULT 12,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8mb4 COMMENT='登录历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_login_log`
--

LOCK TABLES `system_oauth_user_login_log` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_login_log` DISABLE KEYS */;
INSERT INTO `system_oauth_user_login_log` VALUES (173,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-10 23:00:43',12),(174,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-10 23:48:40',12),(175,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-10 23:51:30',12),(176,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-11 00:03:45',12),(177,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-11 10:19:08',12),(178,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-11 22:21:23',12),(179,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 00:54:43',12),(180,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 01:53:02',12),(181,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 02:03:17',12),(182,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 09:55:31',12),(183,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:13:43',12),(184,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:15:47',12),(185,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:17:54',12),(186,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:20:59',12),(187,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:25:13',12),(188,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:27:01',12),(189,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 10:27:59',12),(190,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 11:04:16',12),(191,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:18:16',12),(192,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:19:20',12),(193,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:24:29',12),(194,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:27:04',12),(195,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:30:00',12),(196,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:32:08',12),(197,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:39:36',12),(198,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:41:36',12),(199,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:45:30',12),(200,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:46:45',12),(201,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 13:47:51',12),(202,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 14:02:51',12),(203,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 14:03:44',12),(204,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 14:05:16',12),(205,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 14:21:44',12),(206,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 14:24:00',12),(207,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:16:38',12),(208,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:21:04',12),(209,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:32:15',12),(210,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:34:22',12),(211,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:41:49',12),(212,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:42:43',12),(213,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 15:56:05',12),(214,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-15 16:11:07',12),(215,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 21:24:51',12),(216,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 21:36:31',12),(217,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 22:41:14',12),(218,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:00:51',12),(219,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:02:07',12),(220,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:13:47',12),(221,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:15:25',12),(222,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:16:02',12),(223,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:23:53',12),(224,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:29:44',12),(225,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:30:41',12),(226,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:32:03',12),(227,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:32:52',12),(228,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:33:37',12),(229,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:41:49',12),(230,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-22 23:53:35',12),(231,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:00:54',12),(232,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:02:48',12),(233,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:03:54',12),(234,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:05:09',12),(235,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:06:19',12),(236,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:11:55',12),(237,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:13:23',12),(238,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:21:21',12),(239,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:22:15',12),(240,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:23:30',12),(241,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 00:55:34',12),(242,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:14:45',12),(243,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:33:12',12),(244,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:34:07',12),(245,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:36:52',12),(246,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:38:04',12),(247,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:39:09',12),(248,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:41:57',12),(249,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:43:04',12),(250,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:50:22',12),(251,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 01:52:21',12),(252,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 02:01:53',12),(253,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 12:24:32',12),(254,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 12:26:32',12),(255,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 12:35:36',12),(256,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 12:36:27',12),(257,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 12:38:40',12),(258,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 13:08:52',12),(259,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:24:25',12),(260,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:29:28',12),(261,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:30:51',12),(262,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:37:10',12),(263,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:38:18',12),(264,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 15:54:37',12),(265,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:01:55',12),(266,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:06:13',12),(267,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:17:23',12),(268,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:27:23',12),(269,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:35:10',12),(270,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:37:44',12),(271,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 16:45:55',12),(272,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:02:49',12),(273,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:22:19',12),(274,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:27:26',12),(275,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:29:45',12),(276,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:34:50',12),(277,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:57:51',12),(278,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 17:58:45',12),(279,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:07:01',12),(280,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:23:17',12),(281,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:26:51',12),(282,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:27:53',12),(283,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:29:55',12),(284,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:38:23',12),(285,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:39:27',12),(286,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:41:40',12),(287,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 20:57:23',12),(288,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-23 21:16:30',12),(289,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-24 00:39:01',12),(290,1000,'127.0.0.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-04-24 11:24:02',12);
/*!40000 ALTER TABLE `system_oauth_user_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_user_password`
--

DROP TABLE IF EXISTS `system_oauth_user_password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_user_password` (
  `id` varchar(32) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `passwd` varchar(50) NOT NULL,
  `salt` varchar(8) NOT NULL,
  `type` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_type_unique` (`uid`,`type`),
  KEY `uid_index` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_password`
--

LOCK TABLES `system_oauth_user_password` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_password` DISABLE KEYS */;
INSERT INTO `system_oauth_user_password` VALUES ('134e71d8327bb6630ffefd9d41458bce',9812,'ed52beee62c0d5d7b92e47d12989afcd','xoEQnI',1),('b49376a143f8441f3f1a94c86252b211',1000,'c11d3ea1b43074c11fc7fdd2d3d2cbba','ntzVMV',1);
/*!40000 ALTER TABLE `system_oauth_user_password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth_user_role`
--

DROP TABLE IF EXISTS `system_oauth_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_oauth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8mb4 COMMENT='管理平台用户与角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_role`
--

LOCK TABLES `system_oauth_user_role` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_role` DISABLE KEYS */;
INSERT INTO `system_oauth_user_role` VALUES (1002,1000,1000),(1003,9812,1000);
/*!40000 ALTER TABLE `system_oauth_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-24 11:37:14
