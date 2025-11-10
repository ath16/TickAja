CREATE DATABASE  IF NOT EXISTS `db_tickaja` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_tickaja`;
-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: localhost    Database: db_tickaja
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `detail_reservasi`
--

DROP TABLE IF EXISTS `detail_reservasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_reservasi` (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `reservasi_id` int NOT NULL,
  `kursi_id` int NOT NULL,
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `reservasi_id` (`reservasi_id`,`kursi_id`),
  KEY `kursi_id` (`kursi_id`),
  CONSTRAINT `detail_reservasi_ibfk_1` FOREIGN KEY (`reservasi_id`) REFERENCES `reservasi` (`reservasi_id`) ON DELETE CASCADE,
  CONSTRAINT `detail_reservasi_ibfk_2` FOREIGN KEY (`kursi_id`) REFERENCES `kursi` (`kursi_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_reservasi`
--

LOCK TABLES `detail_reservasi` WRITE;
/*!40000 ALTER TABLE `detail_reservasi` DISABLE KEYS */;
/*!40000 ALTER TABLE `detail_reservasi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `film`
--

DROP TABLE IF EXISTS `film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `film` (
  `film_id` int NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `deskripsi` text,
  `durasi_menit` int DEFAULT NULL,
  `poster_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`film_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jadwal_tayang`
--

DROP TABLE IF EXISTS `jadwal_tayang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jadwal_tayang` (
  `jadwal_id` int NOT NULL AUTO_INCREMENT,
  `film_id` int NOT NULL,
  `studio_id` int NOT NULL,
  `waktu_tayang` datetime NOT NULL,
  `harga_tiket` int NOT NULL,
  PRIMARY KEY (`jadwal_id`),
  KEY `film_id` (`film_id`),
  KEY `studio_id` (`studio_id`),
  CONSTRAINT `jadwal_tayang_ibfk_1` FOREIGN KEY (`film_id`) REFERENCES `film` (`film_id`) ON DELETE CASCADE,
  CONSTRAINT `jadwal_tayang_ibfk_2` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`studio_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jadwal_tayang`
--

LOCK TABLES `jadwal_tayang` WRITE;
/*!40000 ALTER TABLE `jadwal_tayang` DISABLE KEYS */;
/*!40000 ALTER TABLE `jadwal_tayang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kursi`
--

DROP TABLE IF EXISTS `kursi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kursi` (
  `kursi_id` int NOT NULL AUTO_INCREMENT,
  `studio_id` int NOT NULL,
  `nomor_baris` char(1) NOT NULL,
  `nomor_kursi` int NOT NULL,
  PRIMARY KEY (`kursi_id`),
  UNIQUE KEY `studio_id` (`studio_id`,`nomor_baris`,`nomor_kursi`),
  CONSTRAINT `kursi_ibfk_1` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`studio_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kursi`
--

LOCK TABLES `kursi` WRITE;
/*!40000 ALTER TABLE `kursi` DISABLE KEYS */;
/*!40000 ALTER TABLE `kursi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lokasi_bioskop`
--

DROP TABLE IF EXISTS `lokasi_bioskop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lokasi_bioskop` (
  `lokasi_id` int NOT NULL AUTO_INCREMENT,
  `nama_lokasi` varchar(255) NOT NULL,
  `alamat` text,
  PRIMARY KEY (`lokasi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lokasi_bioskop`
--

LOCK TABLES `lokasi_bioskop` WRITE;
/*!40000 ALTER TABLE `lokasi_bioskop` DISABLE KEYS */;
/*!40000 ALTER TABLE `lokasi_bioskop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pengguna`
--

DROP TABLE IF EXISTS `pengguna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pengguna` (
  `pengguna_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` enum('admin','user') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pengguna_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pengguna`
--

LOCK TABLES `pengguna` WRITE;
/*!40000 ALTER TABLE `pengguna` DISABLE KEYS */;
/*!40000 ALTER TABLE `pengguna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservasi`
--

DROP TABLE IF EXISTS `reservasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservasi` (
  `reservasi_id` int NOT NULL AUTO_INCREMENT,
  `pengguna_id` int NOT NULL,
  `jadwal_id` int DEFAULT NULL,
  `total_harga` int NOT NULL,
  `status_pembayaran` enum('pending','sukses','batal') DEFAULT 'pending',
  `waktu_reservasi` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reservasi_id`),
  KEY `pengguna_id` (`pengguna_id`),
  KEY `jadwal_id` (`jadwal_id`),
  CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`pengguna_id`) REFERENCES `pengguna` (`pengguna_id`),
  CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`jadwal_id`) REFERENCES `jadwal_tayang` (`jadwal_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservasi`
--

LOCK TABLES `reservasi` WRITE;
/*!40000 ALTER TABLE `reservasi` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservasi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studio`
--

DROP TABLE IF EXISTS `studio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studio` (
  `studio_id` int NOT NULL AUTO_INCREMENT,
  `lokasi_id` int NOT NULL,
  `nama_studio` varchar(100) NOT NULL,
  PRIMARY KEY (`studio_id`),
  KEY `lokasi_id` (`lokasi_id`),
  CONSTRAINT `studio_ibfk_1` FOREIGN KEY (`lokasi_id`) REFERENCES `lokasi_bioskop` (`lokasi_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studio`
--

LOCK TABLES `studio` WRITE;
/*!40000 ALTER TABLE `studio` DISABLE KEYS */;
/*!40000 ALTER TABLE `studio` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-10 17:01:26
