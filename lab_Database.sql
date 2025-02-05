-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lab
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `AdminID` int NOT NULL,
  `Users_UserID` int NOT NULL,
  PRIMARY KEY (`AdminID`),
  KEY `fk_Admins_Users1_idx` (`Users_UserID`),
  CONSTRAINT `fk_Admins_Users1` FOREIGN KEY (`Users_UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointments` (
  `AppointmentID` int NOT NULL AUTO_INCREMENT,
  `AppointmentDate` date DEFAULT NULL,
  `Appointment Time` varchar(45) DEFAULT NULL,
  `Status` enum('Pending','Confirmed','Completed','Cancelled') DEFAULT NULL,
  `Patients_PatientID` int NOT NULL,
  PRIMARY KEY (`AppointmentID`),
  KEY `fk_Appointments_Patients1_idx` (`Patients_PatientID`),
  CONSTRAINT `fk_Appointments_Patients1` FOREIGN KEY (`Patients_PatientID`) REFERENCES `patients` (`PatientID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,'2025-12-01','12:30','Completed',1234),(2,'2025-01-08','12:30','Pending',1234);
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodpresuretests`
--

DROP TABLE IF EXISTS `bloodpresuretests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bloodpresuretests` (
  `BloodPresureTestID` int NOT NULL,
  `BloodPresureTest1` int DEFAULT NULL,
  `BloodPresureTest2` int DEFAULT NULL,
  `BloodPresureTest3` int DEFAULT NULL,
  `Patients_PatientID` int NOT NULL,
  `average` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BloodPresureTestID`),
  KEY `fk_BloodPresureTests_Patients1_idx` (`Patients_PatientID`),
  CONSTRAINT `fk_BloodPresureTests_Patients1` FOREIGN KEY (`Patients_PatientID`) REFERENCES `patients` (`PatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodpresuretests`
--

LOCK TABLES `bloodpresuretests` WRITE;
/*!40000 ALTER TABLE `bloodpresuretests` DISABLE KEYS */;
/*!40000 ALTER TABLE `bloodpresuretests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bloodsugartests`
--

DROP TABLE IF EXISTS `bloodsugartests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bloodsugartests` (
  `BloodSugarTestID` int NOT NULL,
  `BloodSugarTest1` int DEFAULT NULL,
  `BloodSugarTest2` int DEFAULT NULL,
  `BloodSugarTest3` int DEFAULT NULL,
  `Patients_PatientID` int NOT NULL,
  `average` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`BloodSugarTestID`),
  KEY `fk_BloodSugarTests_Patients1_idx` (`Patients_PatientID`),
  CONSTRAINT `fk_BloodSugarTests_Patients1` FOREIGN KEY (`Patients_PatientID`) REFERENCES `patients` (`PatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloodsugartests`
--

LOCK TABLES `bloodsugartests` WRITE;
/*!40000 ALTER TABLE `bloodsugartests` DISABLE KEYS */;
INSERT INTO `bloodsugartests` VALUES (1,120,90,83,1234,97.66666666666667,'Normal');
/*!40000 ALTER TABLE `bloodsugartests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `labworkerusers`
--

DROP TABLE IF EXISTS `labworkerusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `labworkerusers` (
  `LabWorkerID` int NOT NULL,
  `Users_UserID` int NOT NULL,
  PRIMARY KEY (`LabWorkerID`),
  KEY `fk_LabWorkerUsers_Users_idx` (`Users_UserID`),
  CONSTRAINT `fk_LabWorkerUsers_Users` FOREIGN KEY (`Users_UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labworkerusers`
--

LOCK TABLES `labworkerusers` WRITE;
/*!40000 ALTER TABLE `labworkerusers` DISABLE KEYS */;
/*!40000 ALTER TABLE `labworkerusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `PatientID` int NOT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `EmergencyContactNum` varchar(45) DEFAULT NULL,
  `EmergencyContactName` varchar(45) DEFAULT NULL,
  `KnownAllergies` varchar(255) DEFAULT NULL,
  `RegistrationDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `Name` varchar(255) NOT NULL,
  PRIMARY KEY (`PatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1234,'2002-05-14','Male','Birzeit','0599121954','Kareem Masalma','Nothing','2024-12-31 18:07:49','Faris');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patientusers`
--

DROP TABLE IF EXISTS `patientusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patientusers` (
  `idPatientUsers` int NOT NULL,
  `PatientUserscol` varchar(45) DEFAULT NULL,
  `Users_UserID` int NOT NULL,
  `Patients_PatientID` int NOT NULL,
  `Admins_AdminID` int NOT NULL,
  PRIMARY KEY (`idPatientUsers`),
  KEY `fk_PatientUsers_Users1_idx` (`Users_UserID`),
  KEY `fk_PatientUsers_Patients1_idx` (`Patients_PatientID`),
  KEY `fk_PatientUsers_Admins1_idx` (`Admins_AdminID`),
  CONSTRAINT `fk_PatientUsers_Admins1` FOREIGN KEY (`Admins_AdminID`) REFERENCES `admins` (`AdminID`),
  CONSTRAINT `fk_PatientUsers_Patients1` FOREIGN KEY (`Patients_PatientID`) REFERENCES `patients` (`PatientID`),
  CONSTRAINT `fk_PatientUsers_Users1` FOREIGN KEY (`Users_UserID`) REFERENCES `users` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patientusers`
--

LOCK TABLES `patientusers` WRITE;
/*!40000 ALTER TABLE `patientusers` DISABLE KEYS */;
/*!40000 ALTER TABLE `patientusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `RateID` int NOT NULL AUTO_INCREMENT,
  `ReceptionTreating` double DEFAULT NULL,
  `LabTreating` double DEFAULT NULL,
  `InterfaceUsability` double DEFAULT NULL,
  `PrivacyandSecurity` double DEFAULT NULL,
  `Notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`RateID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,10,5,3,7,'Good'),(2,2,5,6,10,'Good');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urinetest`
--

DROP TABLE IF EXISTS `urinetest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `urinetest` (
  `UrineTestID` int NOT NULL,
  `UrineTest1` int DEFAULT NULL,
  `UrineTest2` int DEFAULT NULL,
  `UrineTest3` int DEFAULT NULL,
  `Patients_PatientID` int NOT NULL,
  `average` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UrineTestID`),
  KEY `fk_UrineTest_Patients1_idx` (`Patients_PatientID`),
  CONSTRAINT `fk_UrineTest_Patients1` FOREIGN KEY (`Patients_PatientID`) REFERENCES `patients` (`PatientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urinetest`
--

LOCK TABLES `urinetest` WRITE;
/*!40000 ALTER TABLE `urinetest` DISABLE KEYS */;
/*!40000 ALTER TABLE `urinetest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(16) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `UserID` int NOT NULL,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `FullName` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `PhoneNumber` varchar(45) DEFAULT NULL,
  `Role` enum('Admin','Receptionist','LabWorker','Patient') DEFAULT NULL,
  `DateCreated` datetime DEFAULT CURRENT_TIMESTAMP,
  `LastModified` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Amer','674552ada3820f7733c110827306af1bd4d2df14a79dd28ced5f41031fc520ad','Amer','amershadi55@gmail.com','0598542299','LabWorker','2025-01-07 12:18:16','2025-01-07 12:18:16'),(1234,'Ahmad','674552ada3820f7733c110827306af1bd4d2df14a79dd28ced5f41031fc520ad','Ahmad','kareemmasalmah6@gmail.com','0599121954','Patient','2025-01-07 13:14:11','2025-01-07 13:14:11'),(1220535,'Kareem Masalma','674552ada3820f7733c110827306af1bd4d2df14a79dd28ced5f41031fc520ad','Kareem Masalma','kareemmasalma273@gmail.com','0599121954','Admin',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-08  1:24:59
