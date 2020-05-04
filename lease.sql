-- PengHao
-- 2020-4-27 18:42:14
-- 1、进入MySQL数据库
-- 2、创建db_lease数据库
-- 3、执行source 此文件路径

-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_lease
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `tbl_admin`
--

DROP TABLE IF EXISTS `tbl_admin`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tbl_admin`
(
    `id`       int(4)      NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username` varchar(12) NOT NULL COMMENT '用户名',
    `password` varchar(12) NOT NULL COMMENT '密码',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_admin`
--

LOCK TABLES `tbl_admin` WRITE;
/*!40000 ALTER TABLE `tbl_admin`
    DISABLE KEYS */;
INSERT INTO `tbl_admin`
VALUES (1, 'admin', '123456');
/*!40000 ALTER TABLE `tbl_admin`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_customer`
--

DROP TABLE IF EXISTS `tbl_customer`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tbl_customer`
(
    `id`          int(11)     NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`        varchar(12) NOT NULL COMMENT '姓名',
    `telephone`   varchar(12) NOT NULL COMMENT '手机号',
    `password`    varchar(12) NOT NULL COMMENT '密码',
    `address`     varchar(32) NOT NULL COMMENT '地址',
    `create_date` date        NOT NULL COMMENT '创建日期',
    `valid`       char(1)     NOT NULL DEFAULT 't' COMMENT '有效性',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8 COMMENT ='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_customer`
--

LOCK TABLES `tbl_customer` WRITE;
/*!40000 ALTER TABLE `tbl_customer`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_customer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_material`
--

DROP TABLE IF EXISTS `tbl_material`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tbl_material`
(
    `id`            int(11)        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `name`          varchar(12)    NOT NULL COMMENT '名称',
    `model`         varchar(12)    NOT NULL COMMENT '型号',
    `specification` decimal(10, 4) NOT NULL COMMENT '规格',
    `unit`          varchar(4)     NOT NULL COMMENT '单位',
    `price`         decimal(10, 6) NOT NULL COMMENT '单价',
    `stocks`        int(11)        NOT NULL COMMENT '库存量',
    `total`         int(11)        NOT NULL COMMENT '总数量',
    `description`   varchar(64)             DEFAULT NULL COMMENT '描述',
    `cost`          decimal(10, 4) NOT NULL DEFAULT '0.0000' COMMENT '赔偿金',
    `valid`         char(1)        NOT NULL DEFAULT 't' COMMENT '有效性',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 44
  DEFAULT CHARSET = utf8 COMMENT ='材料表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_material`
--

LOCK TABLES `tbl_material` WRITE;
/*!40000 ALTER TABLE `tbl_material`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_material`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order`
--

DROP TABLE IF EXISTS `tbl_order`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tbl_order`
(
    `id`            varchar(17) NOT NULL COMMENT '编号',
    `c_id`          int(11)     NOT NULL COMMENT '客户编号',
    `type`          varchar(2)  NOT NULL COMMENT '租/还',
    `creation_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `valid`         char(1)     NOT NULL DEFAULT 't' COMMENT '有效性',
    PRIMARY KEY (`id`),
    KEY `tbl_order_tbl_customer_id_fk` (`c_id`),
    CONSTRAINT `tbl_order_tbl_customer_id_fk` FOREIGN KEY (`c_id`) REFERENCES `tbl_customer` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order`
--

LOCK TABLES `tbl_order` WRITE;
/*!40000 ALTER TABLE `tbl_order`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_order`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_order_detail`
--

DROP TABLE IF EXISTS `tbl_order_detail`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
SET character_set_client = utf8mb4;
CREATE TABLE `tbl_order_detail`
(
    `id`            varchar(17) NOT NULL COMMENT '编号',
    `o_id`          varchar(17) NOT NULL COMMENT '订单编号',
    `m_id`          int(11)     NOT NULL COMMENT '材料编号',
    `amount`        int(11)     NOT NULL COMMENT '数量',
    `creation_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `valid`         char(1)     NOT NULL DEFAULT 't' COMMENT '有效性',
    PRIMARY KEY (`id`),
    KEY `m_id` (`m_id`),
    KEY `tbl_order_detail_ibfk_2` (`o_id`),
    CONSTRAINT `tbl_order_detail_ibfk_1` FOREIGN KEY (`m_id`) REFERENCES `tbl_material` (`id`),
    CONSTRAINT `tbl_order_detail_ibfk_2` FOREIGN KEY (`o_id`) REFERENCES `tbl_order` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_order_detail`
--

LOCK TABLES `tbl_order_detail` WRITE;
/*!40000 ALTER TABLE `tbl_order_detail`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_order_detail`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2020-04-27 18:31:58
