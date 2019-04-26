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
) ENGINE=InnoDB AUTO_INCREMENT=432 DEFAULT CHARSET=utf8mb4 COMMENT='管理平台角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_role_authorities`
--

LOCK TABLES `system_oauth_role_authorities` WRITE;
/*!40000 ALTER TABLE `system_oauth_role_authorities` DISABLE KEYS */;
INSERT INTO `system_oauth_role_authorities` VALUES (403,'demoPlugin.demoModule1.demo.menu1.find',1000),(404,'demoPlugin.demoModule2.demo.menu21.find',1000),(405,'fortGateway.hostManager.group.find',1000),(406,'fortGateway.hostManager.group.add',1000),(407,'fortGateway.hostManager.group.edit',1000),(408,'fortGateway.hostManager.group.delete',1000),(409,'fortGateway.hostManager.group.authority',1000),(410,'fortGateway.hostManager.auth.find',1000),(411,'fortGateway.hostManager.auth.add',1000),(412,'fortGateway.hostManager.auth.edit',1000),(413,'fortGateway.hostManager.auth.delete',1000),(414,'fortGateway.hostManager.host.find',1000),(415,'fortGateway.hostManager.host.add',1000),(416,'fortGateway.hostManager.host.edit',1000),(417,'fortGateway.hostManager.host.delete',1000),(418,'fortGateway.hostManager.host.terminal',1000),(419,'system.oauth.user.find',1000),(420,'system.oauth.user.add',1000),(421,'system.oauth.user.enable',1000),(422,'system.oauth.user.disable',1000),(423,'system.oauth.user.resetPassword',1000),(424,'system.oauth.user.roles',1000),(425,'system.oauth.role.find',1000),(426,'system.oauth.role.add',1000),(427,'system.oauth.role.edit',1000),(428,'system.oauth.role.delete',1000),(429,'system.oauth.role.authority',1000),(430,'system.setting.config.find',1000),(431,'system.setting.config.edit',1000);
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
) ENGINE=InnoDB AUTO_INCREMENT=9814 DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_base_info`
--

LOCK TABLES `system_oauth_user_base_info` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_base_info` DISABLE KEYS */;
INSERT INTO `system_oauth_user_base_info` VALUES (1000,'管理员','http://static.shuci.net/vk/images/defultAvatar.jpg',2,1,'2017-06-05 22:32:40','115.231.223.228');
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
INSERT INTO `system_oauth_user_login_account` VALUES ('10000',1000,'admin',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=297 DEFAULT CHARSET=utf8mb4 COMMENT='登录历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth_user_login_log`
--

LOCK TABLES `system_oauth_user_login_log` WRITE;
/*!40000 ALTER TABLE `system_oauth_user_login_log` DISABLE KEYS */;
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
INSERT INTO `system_oauth_user_password` VALUES ('b49376a143f8441f3f1a94c86252b211',1000,'5a6c8f88d0bf8f3850b76498a9e4c605','cUCFg9',1);
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
INSERT INTO `system_oauth_user_role` VALUES (1002,1000,1000);
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

-- Dump completed on 2019-04-27  2:15:35
