-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 14, 2026 at 12:32 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_rq_alhafizh`
--

-- --------------------------------------------------------

--
-- Table structure for table `kelas_mengaji`
--

CREATE TABLE `kelas_mengaji` (
  `kode_kelas` int NOT NULL,
  `nama_kelas` varchar(100) NOT NULL,
  `pengajar_id` int NOT NULL,
  `kapasitas` int NOT NULL
) ;

--
-- Dumping data for table `kelas_mengaji`
--

INSERT INTO `kelas_mengaji` (`kode_kelas`, `nama_kelas`, `pengajar_id`, `kapasitas`) VALUES
(1, 'UMMI 1', 1, 30),
(2, 'UMMI 2', 2, 30),
(3, 'UMMI 3', 3, 30),
(4, 'UMMI 4', 4, 30),
(5, 'UMMI 5', 5, 30),
(6, 'UMMI 6', 6, 30);

-- --------------------------------------------------------

--
-- Table structure for table `pengajar`
--

CREATE TABLE `pengajar` (
  `id` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `nomor_telepon` varchar(15) NOT NULL,
  `mengajar` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pengajar`
--

INSERT INTO `pengajar` (`id`, `nama`, `nomor_telepon`, `mengajar`) VALUES
(1, 'Ibu Ana', '081111111111', 'UMMI 1'),
(2, 'Ibu Fatimah', '081222222222', 'UMMI 2'),
(3, 'Ibu Hajrah', '081333333333', 'UMMI 3'),
(4, 'Ibu Alya', '081444444444', 'UMMI 4'),
(5, 'Ibu Khansa', '081555555555', 'UMMI 5'),
(6, 'Ibu Gita', '081666666666', 'UMMI 6');

-- --------------------------------------------------------

--
-- Table structure for table `santri`
--

CREATE TABLE `santri` (
  `id` int NOT NULL,
  `nama` varchar(100) NOT NULL,
  `umur` int NOT NULL,
  `nomor_telepon_wali` varchar(15) NOT NULL,
  `kode_kelas` int NOT NULL
) ;

--
-- Dumping data for table `santri`
--

INSERT INTO `santri` (`id`, `nama`, `umur`, `nomor_telepon_wali`, `kode_kelas`) VALUES
(1, 'Ahmad Fauzan', 10, '081234567890', 1),
(2, 'Aisyah Putri', 8, '081298765432', 1),
(3, 'Muhammad Rizky', 12, '082112223333', 2),
(4, 'Siti Khadijah', 9, '082233445566', 2),
(5, 'Abdullah Faqih', 11, '083344556677', 3),
(6, 'Fatimah Zahra', 7, '083455667788', 3),
(7, 'Ali Imran', 13, '084566778899', 4),
(8, 'Maryam Aulia', 10, '084677889900', 4),
(9, 'Hasan Basri', 14, '085788990011', 5),
(10, 'Hana Safitri', 8, '085899001122', 5),
(11, 'Yusuf Maulana', 15, '086900112233', 6),
(12, 'Khadijah Nabila', 11, '086011223344', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kelas_mengaji`
--
ALTER TABLE `kelas_mengaji`
  ADD PRIMARY KEY (`kode_kelas`),
  ADD KEY `fk_kelas_pengajar` (`pengajar_id`);

--
-- Indexes for table `pengajar`
--
ALTER TABLE `pengajar`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `santri`
--
ALTER TABLE `santri`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_santri_kelas` (`kode_kelas`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kelas_mengaji`
--
ALTER TABLE `kelas_mengaji`
  MODIFY `kode_kelas` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pengajar`
--
ALTER TABLE `pengajar`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `santri`
--
ALTER TABLE `santri`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `kelas_mengaji`
--
ALTER TABLE `kelas_mengaji`
  ADD CONSTRAINT `fk_kelas_pengajar` FOREIGN KEY (`pengajar_id`) REFERENCES `pengajar` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `santri`
--
ALTER TABLE `santri`
  ADD CONSTRAINT `fk_santri_kelas` FOREIGN KEY (`kode_kelas`) REFERENCES `kelas_mengaji` (`kode_kelas`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
