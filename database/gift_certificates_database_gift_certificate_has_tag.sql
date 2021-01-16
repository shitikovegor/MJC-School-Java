-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gift_certificates_database
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gift_certificate_has_tag`
--

DROP TABLE IF EXISTS `gift_certificate_has_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gift_certificate_has_tag` (
  `gift_certificate_id_fk` bigint NOT NULL,
  `tag_id_fk` bigint NOT NULL,
  PRIMARY KEY (`gift_certificate_id_fk`,`tag_id_fk`),
  KEY `fk_gift_certificate_has_tag_tag1_idx` (`tag_id_fk`),
  KEY `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id_fk`),
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate` FOREIGN KEY (`gift_certificate_id_fk`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `fk_gift_certificate_has_tag_tag1` FOREIGN KEY (`tag_id_fk`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gift_certificate_has_tag`
--

LOCK TABLES `gift_certificate_has_tag` WRITE;
/*!40000 ALTER TABLE `gift_certificate_has_tag` DISABLE KEYS */;
INSERT INTO `gift_certificate_has_tag` VALUES (4,5),(7,5),(8,7),(8,8),(9,9),(10,9),(12,9),(14,9),(10,14),(12,16),(14,16);
/*!40000 ALTER TABLE `gift_certificate_has_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-16 11:29:09
