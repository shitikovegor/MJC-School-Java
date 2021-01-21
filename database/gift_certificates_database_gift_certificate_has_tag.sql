-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gift_certificates_database
-- ------------------------------------------------------
-- Server version	8.0.21

--
-- Table structure for table `gift_certificate_has_tag`
--

DROP TABLE IF EXISTS `gift_certificate_has_tag`;
CREATE TABLE `gift_certificate_has_tag` (
  `gift_certificate_id_fk` bigint NOT NULL,
  `tag_id_fk` bigint NOT NULL,
  PRIMARY KEY (`gift_certificate_id_fk`,`tag_id_fk`),
  KEY `fk_gift_certificate_has_tag_tag1_idx` (`tag_id_fk`),
  KEY `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id_fk`),
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate` FOREIGN KEY (`gift_certificate_id_fk`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `fk_gift_certificate_has_tag_tag1` FOREIGN KEY (`tag_id_fk`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gift_certificate_has_tag`
--

LOCK TABLES `gift_certificate_has_tag` WRITE;
INSERT INTO `gift_certificate_has_tag` VALUES (4,5),(7,5),(8,7),(8,8),(9,9),(10,9),(12,9),(14,9),(10,14),(12,16),(14,16);
UNLOCK TABLES;

-- Dump completed on 2021-01-16 11:29:09
