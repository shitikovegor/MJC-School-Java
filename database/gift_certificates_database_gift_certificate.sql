-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gift_certificates_database
-- ------------------------------------------------------
-- Server version	8.0.21

--
-- Table structure for table `gift_certificate`
--

DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE `gift_certificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration` int NOT NULL,
  `create_date` datetime NOT NULL,
  `last_update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gift_certificate`
--

LOCK TABLES `gift_certificate` WRITE;
INSERT INTO `gift_certificate` VALUES (4,'Ocean rest','Sea cruise',360.50,10,'2021-01-13 10:00:00','2021-01-14 17:28:22'),(7,'Ocean rest','Sea cruise',350.50,10,'2021-01-13 16:00:00','2021-01-13 16:51:48'),(8,'Snowboarding','Snowboarding in Karpaty, 2 days',299.99,30,'2021-01-12 15:00:00','2021-01-12 15:00:00'),(9,'Sushi set','Sushi, 2 kilos!',45.00,20,'2021-01-11 18:00:00','2021-01-11 18:00:00'),(10,'Pizza set1','Pizza, 36cm',14.99,23,'2021-01-14 18:00:00','2021-01-14 18:00:00'),(12,'Very big sushi set','Sushi, 15rolls',30.00,10,'2021-01-15 14:13:39','2021-01-16 11:25:36'),(14,'Sushi set4','California, 1kilo!!!',35.00,23,'2021-01-15 17:46:58','2021-01-16 11:27:03');
UNLOCK TABLES;

-- Dump completed on 2021-01-16 11:29:11
