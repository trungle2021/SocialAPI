CREATE DATABASE  IF NOT EXISTS `social` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `social`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: social
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `accounts_roles_id_fk` (`role_id`),
  CONSTRAINT `accounts_roles_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES ('0d108b1d-fed0-11ed-9374-a8a15917434f','admin@gmail.com','123',_binary '\0','b1bdf197-fecd-11ed-9374-a8a15917434f'),('2a72c9ca-fed0-11ed-9374-a8a15917434f','normal@gmail.com','123',_binary '\0','7f6424d0-fecf-11ed-9374-a8a15917434f'),('6b3276e9-fed0-11ed-9374-a8a15917434f','employee@gmail.com','123',_binary '\0','b7147983-fece-11ed-9374-a8a15917434f'),('7fce4bfe-fed7-11ed-9374-a8a15917434f','employee1@gmail.com','123',_binary '\0','b7147983-fece-11ed-9374-a8a15917434f');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_image_posts`
--

DROP TABLE IF EXISTS `comment_image_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_image_posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `user_id` varchar(36) DEFAULT NULL,
  `image_id` varchar(36) NOT NULL,
  `content` longtext,
  `posted_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`image_id`),
  KEY `user_id` (`user_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `comment_image_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `comment_image_posts_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `post_images` (`id`),
  CONSTRAINT `FK6q23h8y73kxbit1r8hqc5ih7y` FOREIGN KEY (`id`) REFERENCES `like_comments_image` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_image_posts`
--

LOCK TABLES `comment_image_posts` WRITE;
/*!40000 ALTER TABLE `comment_image_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_image_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_posts`
--

DROP TABLE IF EXISTS `comment_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `user_id` varchar(36) DEFAULT NULL,
  `post_id` varchar(36) NOT NULL,
  `content` longtext,
  `posted_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`post_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `comment_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `comment_posts_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `FKesu6vpt74aefx0d9o0oqnrgtp` FOREIGN KEY (`id`) REFERENCES `like_comment_posts` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_posts`
--

LOCK TABLES `comment_posts` WRITE;
/*!40000 ALTER TABLE `comment_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `user_id` varchar(36) NOT NULL,
  `user_friend_id` varchar(36) NOT NULL,
  `establish_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`user_friend_id`),
  KEY `user_friend_id` (`user_friend_id`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`user_friend_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_comment_posts`
--

DROP TABLE IF EXISTS `like_comment_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_comment_posts` (
  `id` varchar(36) NOT NULL,
  `comment_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `liked_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_id` (`comment_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `like_comment_posts_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comment_posts` (`id`),
  CONSTRAINT `like_comment_posts_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_comment_posts`
--

LOCK TABLES `like_comment_posts` WRITE;
/*!40000 ALTER TABLE `like_comment_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_comment_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_comments_image`
--

DROP TABLE IF EXISTS `like_comments_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_comments_image` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `comment_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `liked_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_id` (`comment_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `like_comments_image_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comment_image_posts` (`id`),
  CONSTRAINT `like_comments_image_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_comments_image`
--

LOCK TABLES `like_comments_image` WRITE;
/*!40000 ALTER TABLE `like_comments_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_comments_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_image_posts`
--

DROP TABLE IF EXISTS `like_image_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_image_posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `user_id` varchar(36) DEFAULT NULL,
  `image_id` varchar(36) NOT NULL,
  `liked_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`,`image_id`),
  KEY `user_id` (`user_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `like_image_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `like_image_posts_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `post_images` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_image_posts`
--

LOCK TABLES `like_image_posts` WRITE;
/*!40000 ALTER TABLE `like_image_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_image_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_posts`
--

DROP TABLE IF EXISTS `like_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like_posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `user_id` varchar(36) DEFAULT NULL,
  `post_id` varchar(36) NOT NULL,
  `liked_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`,`post_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `like_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `like_posts_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_posts`
--

LOCK TABLES `like_posts` WRITE;
/*!40000 ALTER TABLE `like_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `like_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marital_status`
--

DROP TABLE IF EXISTS `marital_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marital_status` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marital_status`
--

LOCK TABLES `marital_status` WRITE;
/*!40000 ALTER TABLE `marital_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `marital_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_images`
--

DROP TABLE IF EXISTS `post_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_images` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `image_url` varchar(45) DEFAULT NULL,
  `post_id` varchar(36) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `post_images_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_images`
--

LOCK TABLES `post_images` WRITE;
/*!40000 ALTER TABLE `post_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tagged_users`
--

DROP TABLE IF EXISTS `post_tagged_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tagged_users` (
  `tagged_user_id` varchar(36) NOT NULL,
  `post_id` varchar(36) NOT NULL,
  `tagged_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`tagged_user_id`,`post_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `post_tagged_users_ibfk_1` FOREIGN KEY (`tagged_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `post_tagged_users_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tagged_users`
--

LOCK TABLES `post_tagged_users` WRITE;
/*!40000 ALTER TABLE `post_tagged_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_tagged_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `content` longtext,
  `post_owner` varchar(36) DEFAULT NULL,
  `privacy_status` varchar(36) DEFAULT NULL,
  `posted_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `post_owner` (`post_owner`),
  KEY `privacy_status` (`privacy_status`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`post_owner`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`privacy_status`) REFERENCES `privacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privacies`
--

DROP TABLE IF EXISTS `privacies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privacies` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `privacy_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privacies`
--

LOCK TABLES `privacies` WRITE;
/*!40000 ALTER TABLE `privacies` DISABLE KEYS */;
/*!40000 ALTER TABLE `privacies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `role_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('7f6424d0-fecf-11ed-9374-a8a15917434f','Normal'),('b1bdf197-fecd-11ed-9374-a8a15917434f','Admin'),('b7147983-fece-11ed-9374-a8a15917434f','Employee');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schools`
--

DROP TABLE IF EXISTS `schools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schools` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `school_name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schools`
--

LOCK TABLES `schools` WRITE;
/*!40000 ALTER TABLE `schools` DISABLE KEYS */;
/*!40000 ALTER TABLE `schools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shared_posts`
--

DROP TABLE IF EXISTS `shared_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shared_posts` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `user_id` varchar(36) DEFAULT NULL,
  `post_id` varchar(36) NOT NULL,
  `shared_at` datetime(6) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`post_id`),
  KEY `user_id` (`user_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `shared_posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `shared_posts_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shared_posts`
--

LOCK TABLES `shared_posts` WRITE;
/*!40000 ALTER TABLE `shared_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `shared_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_marital_status`
--

DROP TABLE IF EXISTS `user_marital_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_marital_status` (
  `user_id` varchar(36) NOT NULL,
  `user_partner_id` varchar(36) DEFAULT NULL,
  `is_accepted` tinyint DEFAULT NULL,
  `from_time` datetime(6) DEFAULT NULL,
  `marital_status_id` varchar(36) DEFAULT NULL,
  `privacy_status_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `user_partner_id` (`user_partner_id`),
  KEY `marital_status_id` (`marital_status_id`),
  KEY `privacy_status_id` (`privacy_status_id`),
  CONSTRAINT `FKecibedqc27lpluhrcv8sy9lx1` FOREIGN KEY (`user_partner_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_marital_status_ibfk_3` FOREIGN KEY (`marital_status_id`) REFERENCES `marital_status` (`id`),
  CONSTRAINT `user_marital_status_ibfk_4` FOREIGN KEY (`privacy_status_id`) REFERENCES `privacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_marital_status`
--

LOCK TABLES `user_marital_status` WRITE;
/*!40000 ALTER TABLE `user_marital_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_marital_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_schools`
--

DROP TABLE IF EXISTS `user_schools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_schools` (
  `user_id` varchar(36) NOT NULL,
  `school_id` varchar(36) NOT NULL,
  `privacy_status_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`school_id`),
  KEY `privacy_status_id` (`privacy_status_id`),
  KEY `userschools_ibfk_2` (`school_id`),
  CONSTRAINT `user_schools_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_schools_ibfk_2` FOREIGN KEY (`school_id`) REFERENCES `schools` (`id`),
  CONSTRAINT `user_schools_ibfk_3` FOREIGN KEY (`privacy_status_id`) REFERENCES `privacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_schools`
--

LOCK TABLES `user_schools` WRITE;
/*!40000 ALTER TABLE `user_schools` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_schools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_workplaces`
--

DROP TABLE IF EXISTS `user_workplaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_workplaces` (
  `user_id` varchar(36) NOT NULL,
  `workplace_id` varchar(36) NOT NULL,
  `privacy_status_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`workplace_id`),
  KEY `privacy_status_id` (`privacy_status_id`),
  KEY `user_workplaces_ibfk_2` (`workplace_id`),
  CONSTRAINT `user_workplaces_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_workplaces_ibfk_2` FOREIGN KEY (`workplace_id`) REFERENCES `workplaces` (`id`),
  CONSTRAINT `user_workplaces_ibfk_3` FOREIGN KEY (`privacy_status_id`) REFERENCES `privacies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_workplaces`
--

LOCK TABLES `user_workplaces` WRITE;
/*!40000 ALTER TABLE `user_workplaces` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_workplaces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `first_name` varchar(30) DEFAULT NULL,
  `last_name` varchar(30) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dOB` date DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `origin` varchar(45) DEFAULT NULL,
  `account_id` varchar(36) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workplaces`
--

DROP TABLE IF EXISTS `workplaces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workplaces` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `company` varchar(45) DEFAULT NULL,
  `position` varchar(20) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workplaces`
--

LOCK TABLES `workplaces` WRITE;
/*!40000 ALTER TABLE `workplaces` DISABLE KEYS */;
/*!40000 ALTER TABLE `workplaces` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-30 17:49:46
