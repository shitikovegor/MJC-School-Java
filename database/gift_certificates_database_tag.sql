-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gift_certificates_database
-- ------------------------------------------------------
-- Server version	8.0.21


--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;

INSERT INTO `tag` VALUES (5,'Rest'),(6,'Cruise'),(7,'Extreme'),(8,'Sport'),(9,'Food'),(10,'SPA'),(14,'Pizza'),(16,'Sushi'),(17,'New');

UNLOCK TABLES;

-- Dump completed on 2021-01-16 11:29:10
