DROP DATABASE IF EXISTS `db_tickaja`;
CREATE DATABASE `db_tickaja`;
USE `db_tickaja`;

-- ==========================================
-- 1. MEMBUAT TABEL
-- ==========================================

-- Tabel Pengguna (Sudah ada kolom no_telepon)
CREATE TABLE `pengguna` (
  `pengguna_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `no_telepon` varchar(20) DEFAULT NULL, -- TAMBAHAN PENTING
  `role` enum('admin','user') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pengguna_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Lokasi
CREATE TABLE `lokasi_bioskop` (
  `lokasi_id` int NOT NULL AUTO_INCREMENT,
  `nama_lokasi` varchar(255) NOT NULL,
  `alamat` text,
  PRIMARY KEY (`lokasi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Studio
CREATE TABLE `studio` (
  `studio_id` int NOT NULL AUTO_INCREMENT,
  `lokasi_id` int NOT NULL,
  `nama_studio` varchar(100) NOT NULL,
  PRIMARY KEY (`studio_id`),
  KEY `lokasi_id` (`lokasi_id`),
  CONSTRAINT `studio_ibfk_1` FOREIGN KEY (`lokasi_id`) REFERENCES `lokasi_bioskop` (`lokasi_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Kursi
CREATE TABLE `kursi` (
  `kursi_id` int NOT NULL AUTO_INCREMENT,
  `studio_id` int NOT NULL,
  `nomor_baris` char(1) NOT NULL,
  `nomor_kursi` int NOT NULL,
  PRIMARY KEY (`kursi_id`),
  UNIQUE KEY `studio_id` (`studio_id`,`nomor_baris`,`nomor_kursi`),
  CONSTRAINT `kursi_ibfk_1` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`studio_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Film
CREATE TABLE `film` (
  `film_id` int NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) NOT NULL,
  `deskripsi` text,
  `durasi_menit` int DEFAULT NULL,
  `poster_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`film_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Jadwal
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Reservasi
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel Detail Reservasi (SUDAH DIPERBAIKI)
CREATE TABLE `detail_reservasi` (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `reservasi_id` int NOT NULL,
  `kursi_id` int NOT NULL,
  `harga_kursi` int NOT NULL, -- INI YANG TADI BIKIN ERROR
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `reservasi_id` (`reservasi_id`,`kursi_id`),
  KEY `kursi_id` (`kursi_id`),
  CONSTRAINT `detail_reservasi_ibfk_1` FOREIGN KEY (`reservasi_id`) REFERENCES `reservasi` (`reservasi_id`) ON DELETE CASCADE,
  CONSTRAINT `detail_reservasi_ibfk_2` FOREIGN KEY (`kursi_id`) REFERENCES `kursi` (`kursi_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- 2. INSERT DUMMY DATA
-- ==========================================

INSERT INTO `pengguna` (`username`, `password`, `nama_lengkap`, `email`, `role`) VALUES
('admin', 'admin123', 'Administrator TickAja', 'admin@tickaja.com', 'admin'),
('user', 'user123', 'User Biasa', 'user@email.com', 'user');

INSERT INTO `lokasi_bioskop` (`nama_lokasi`, `alamat`) VALUES
('Grand Indonesia', 'Jl. M.H. Thamrin No.1, Jakarta Pusat'),
('Beachwalk Bali', 'Jl. Pantai Kuta, Badung, Bali');

INSERT INTO `studio` (`lokasi_id`, `nama_studio`) VALUES
(1, 'Studio 1 (Regular)'),
(1, 'Studio 2 (IMAX)'),
(2, 'Studio 1 (Premiere)');

-- Isi Kursi Studio 1 (Baris A dan B)
INSERT INTO `kursi` (`studio_id`, `nomor_baris`, `nomor_kursi`) VALUES
(1, 'A', 1), (1, 'A', 2), (1, 'A', 3), (1, 'A', 4), (1, 'A', 5),
(1, 'B', 1), (1, 'B', 2), (1, 'B', 3), (1, 'B', 4), (1, 'B', 5);

INSERT INTO `film` (`judul`, `deskripsi`, `durasi_menit`, `poster_url`) VALUES
('Avengers: Endgame', 'Para Avengers yang tersisa harus berkumpul kembali.', 181, 'avengers.jpg'),
('Spider-Man: No Way Home', 'Identitas Peter Parker terungkap.', 148, 'spiderman.jpg');

-- Jadwal Tayang (Pastikan tanggalnya valid)
INSERT INTO `jadwal_tayang` (`film_id`, `studio_id`, `waktu_tayang`, `harga_tiket`) VALUES
(1, 1, '2026-02-10 14:00:00', 50000),
(2, 2, '2026-02-10 16:00:00', 75000);