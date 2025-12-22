-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: shoppingmobileapp
-- ------------------------------------------------------
-- Server version	8.4.4

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
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `quantity` int NOT NULL,
  `cart_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,NULL,_binary '\0',NULL,5,2,3),(2,NULL,_binary '\0',NULL,4,2,4),(5,NULL,_binary '\0',NULL,8,6,5),(12,NULL,_binary '\0',NULL,0,10,1),(13,NULL,_binary '\0',NULL,0,10,2);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_64t7ox312pqal3p7fg9o503c2` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (2,NULL,_binary '\0',NULL,_binary '\0',4),(3,NULL,_binary '\0',NULL,_binary '\0',5),(6,NULL,_binary '\0',NULL,_binary '',6),(10,NULL,_binary '\0',NULL,_binary '',7);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'2025-12-18 16:33:49.000000',_binary '\0','2025-12-18 16:33:49.000000',_binary '','Danh mục các sản phẩm điện thoại di động, smartphone','Điện thoại'),(2,'2025-12-18 16:33:49.000000',_binary '\0','2025-12-18 16:33:49.000000',_binary '','Tai nghe, sạc, cáp, ốp lưng và phụ kiện đi kèm','Phụ kiện'),(3,'2025-12-18 16:33:49.000000',_binary '\0','2025-12-18 16:33:49.000000',_binary '','Laptop văn phòng, học tập và gaming','Laptop'),(4,'2025-12-18 16:33:49.000000',_binary '\0','2025-12-18 16:33:49.000000',_binary '','Đồng hồ thông minh, vòng đeo tay, thiết bị IoT','Thiết bị thông minh'),(5,'2025-12-18 16:33:49.000000',_binary '\0','2025-12-18 16:33:49.000000',_binary '','Loa bluetooth, loa kéo, thiết bị âm thanh','Âm thanh');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (3,NULL,_binary '\0',NULL,18990000.00,5,2,3),(4,NULL,_binary '\0',NULL,5990000.00,4,2,4),(5,NULL,_binary '\0',NULL,5990000.00,1,3,4),(6,NULL,_binary '\0',NULL,290000.00,3,3,5),(7,NULL,_binary '\0',NULL,5990000.00,1,4,4),(8,NULL,_binary '\0',NULL,290000.00,12,4,5),(9,NULL,_binary '\0',NULL,5990000.00,1,5,4),(10,NULL,_binary '\0',NULL,290000.00,1,5,5),(11,NULL,_binary '\0',NULL,290000.00,1,6,5),(12,NULL,_binary '\0',NULL,26990000.00,1,6,6),(13,NULL,_binary '\0',NULL,34990000.00,1,6,1),(14,NULL,_binary '\0',NULL,34990000.00,1,7,1),(15,NULL,_binary '\0',NULL,32990000.00,1,7,2),(16,NULL,_binary '\0',NULL,26990000.00,0,8,6),(17,NULL,_binary '\0',NULL,32990000.00,0,8,2),(18,NULL,_binary '\0',NULL,34990000.00,0,8,1),(19,NULL,_binary '\0',NULL,35000.00,2,8,29);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,NULL,_binary '\0',NULL,'NEW',118910000.00,4),(3,NULL,_binary '\0',NULL,'NEW',6860000.00,5),(4,NULL,_binary '\0',NULL,'NEW',9470000.00,5),(5,NULL,_binary '\0',NULL,'NEW',6280000.00,5),(6,NULL,_binary '\0',NULL,'NEW',62270000.00,5),(7,NULL,_binary '\0',NULL,'NEW',67980000.00,7),(8,NULL,_binary '\0',NULL,'NEW',70000.00,5);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `attempt_count` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `send_type` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `otp_chk_1` CHECK ((`send_type` between 0 and 2)),
  CONSTRAINT `otp_chk_2` CHECK ((`status` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES (11,'2025-12-18 15:46:51.878000',_binary '\0','2025-12-18 15:46:51.878000',3,'uchihabi1gio@gmail.com','569144','0982957084',1,0),(12,'2025-12-18 15:47:25.411000',_binary '\0','2025-12-18 15:47:25.411000',2,'uchihabigio@gmail.com','577024','0982957084',1,1),(13,'2025-12-18 16:13:51.967000',_binary '\0','2025-12-18 16:13:51.967000',2,'wearingarmor12345@gmail.com','586513','0867276214',1,1),(14,'2025-12-18 16:58:54.871000',_binary '\0','2025-12-18 16:58:54.871000',2,'trinhhuuhung92@gmail.com','791845','0123456189',1,1),(15,'2025-12-21 02:24:26.288000',_binary '\0','2025-12-21 02:24:26.288000',2,'trinhhuuhung92@gmail.com','067514','0567456783',1,1);
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `can_approval` bit(1) DEFAULT NULL,
  `can_decision` bit(1) DEFAULT NULL,
  `can_view` bit(1) DEFAULT NULL,
  `can_write` bit(1) DEFAULT NULL,
  `parent_permission` enum('CONFIG','STATISTIC') DEFAULT NULL,
  `permission` enum('ACCOUNT','DASHBOARD','DEVICE','MERCHANT','MERCHANT_BRANCH','PROVIDER_CONTENT','PROVIDER_DEVICE','ROLE','SONG') DEFAULT NULL,
  `status` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `permissions_chk_1` CHECK ((`status` between 0 and 1)),
  CONSTRAINT `permissions_chk_2` CHECK ((`type` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `stock` int DEFAULT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','iPhone 15 Pro Max 256GB, chip A17 Pro, camera 48MP','iPhone 15 Pro Max',34990000.00,25,1),(2,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Galaxy S24 Ultra, màn hình 6.8 inch, bút S-Pen','Samsung Galaxy S24 Ultra',32990000.00,30,1),(3,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Xiaomi 14, Snapdragon 8 Gen 3, pin 4610mAh','Xiaomi 14',18990000.00,40,1),(4,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Tai nghe không dây Apple AirPods Pro 2 chống ồn chủ động','Tai nghe AirPods Pro 2',5990000.00,60,2),(5,'2025-12-19 01:41:13.000000',_binary '','2025-12-19 01:41:13.000000',_binary '','Cáp sạc nhanh USB-C to C Anker 60W, dài 1m','AirPods Pro 2 (USB-C)',6290000.00,8,2),(6,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','MacBook Air M2 13 inch, RAM 8GB, SSD 256GB','MacBook Air M2',26990000.00,20,3),(7,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Dell XPS 13, Intel Core i7, màn hình InfinityEdge','Dell XPS 13',31990000.00,15,3),(8,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Apple Watch Series 9 GPS 45mm','Apple Watch Series 9',11990000.00,50,4),(9,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Vòng đeo tay thông minh Xiaomi Mi Band 8','Xiaomi Mi Band 8',990000.00,100,4),(10,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Loa Bluetooth JBL Charge 5 chống nước IP67','Loa JBL Charge 5',4290000.00,35,5),(11,'2025-12-19 01:41:13.000000',_binary '\0','2025-12-19 01:41:13.000000',_binary '','Loa Bluetooth Sony SRS-XE200 nhỏ gọn','Loa Sony SRS-XE200',2490000.00,45,5),(12,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','iPhone 15 Pro Max 256GB, chip A17 Pro, camera 48MP','iPhone 15 Pro Max',34990000.00,25,1),(13,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Galaxy S24 Ultra, màn hình 6.8 inch, bút S-Pen','Samsung Galaxy S24 Ultra',32990000.00,30,1),(14,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Xiaomi 14, Snapdragon 8 Gen 3, pin 4610mAh','Xiaomi 14',18990000.00,40,1),(15,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Tai nghe không dây Apple AirPods Pro 2 chống ồn chủ động','Tai nghe AirPods Pro 2',5990000.00,60,2),(16,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Cáp sạc nhanh USB-C to C Anker 60W, dài 1m','Cáp sạc USB-C Anker',290000.00,150,2),(17,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','MacBook Air M2 13 inch, RAM 8GB, SSD 256GB','MacBook Air M2',26990000.00,20,3),(18,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Dell XPS 13, Intel Core i7, màn hình InfinityEdge','Dell XPS 13',31990000.00,15,3),(19,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Apple Watch Series 9 GPS 45mm','Apple Watch Series 9',11990000.00,50,4),(20,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Vòng đeo tay thông minh Xiaomi Mi Band 8','Xiaomi Mi Band 8',990000.00,100,4),(21,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Loa Bluetooth JBL Charge 5 chống nước IP67','Loa JBL Charge 5',4290000.00,35,5),(22,'2025-12-19 01:44:39.000000',_binary '\0','2025-12-19 01:44:39.000000',_binary '','Loa Bluetooth Sony SRS-XE200 nhỏ gọn','Loa Sony SRS-XE200',2490000.00,45,5),(23,NULL,_binary '\0',NULL,_binary '','Chống ồn chủ động','Tai nghe AirPods Pro 2',5990000.00,10,2),(24,NULL,_binary '\0',NULL,_binary '','chuyên đấm nhau','Găng tay tập gym',5990000.00,10,2),(25,NULL,_binary '\0',NULL,_binary '','Chống ồn chủ động','Tai nghe AirPods Pro 2',5990000.00,10,3),(26,NULL,_binary '\0',NULL,_binary '','Chống ồn chủ động','Tai nghe AirPods Pro 3',5990000.00,10,3),(29,NULL,_binary '\0',NULL,_binary '','tạ tập tay , có các phân loại 3kg và 2kg','Tạ tay nhựa 3kg',35000.00,69,2);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `rating` int NOT NULL,
  `product_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (2,NULL,_binary '\0',NULL,'Sản phẩm rất tốt',5,2,5),(3,NULL,_binary '\0',NULL,'abcdefghiklm',4,1,5),(4,NULL,_binary '\0',NULL,'cảm ơn bạn',5,3,5),(5,NULL,_binary '\0',NULL,'abc',5,5,5),(6,NULL,_binary '\0',NULL,'rất là tệ',5,29,5);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `can_approval` bit(1) DEFAULT NULL,
  `can_decision` bit(1) DEFAULT NULL,
  `can_view` bit(1) DEFAULT NULL,
  `can_write` bit(1) DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `object_id` int NOT NULL,
  `status` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `roles_chk_1` CHECK ((`status` between 0 and 1)),
  CONSTRAINT `roles_chk_2` CHECK ((`type` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2025-12-17 00:00:00.000000',_binary '\0','2025-12-17 00:00:00.000000','USER','Default user role',0,1,0),(2,'2025-12-17 00:00:00.000000',_binary '\0','2025-12-17 00:00:00.000000','ADMIN','Administrator role',0,1,1),(3,NULL,_binary '\0',NULL,'Tran Quoc Thai',NULL,0,1,NULL),(4,NULL,_binary '\0',NULL,'Tran Quoc Thai',NULL,0,1,NULL),(5,NULL,_binary '\0',NULL,'Tran Quoc Thai',NULL,0,1,NULL),(6,NULL,_binary '\0',NULL,'Tran Quoc Thai',NULL,0,1,NULL),(7,NULL,_binary '\0',NULL,'Trinh Huu Hung',NULL,0,1,NULL),(8,NULL,_binary '\0',NULL,'Trinh Huu Hung',NULL,0,1,NULL),(9,NULL,_binary '\0',NULL,'Trinh Huu Hung A',NULL,0,1,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upload_files`
--

DROP TABLE IF EXISTS `upload_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upload_files` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `height` int DEFAULT NULL,
  `origin_file_path` varchar(255) DEFAULT NULL,
  `origin_url` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `thumb_file_path` varchar(255) DEFAULT NULL,
  `thumb_url` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `width` int DEFAULT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkso3ujjcvvck0cwylab5n7hce` (`product_id`),
  CONSTRAINT `FKkso3ujjcvvck0cwylab5n7hce` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upload_files`
--

LOCK TABLES `upload_files` WRITE;
/*!40000 ALTER TABLE `upload_files` DISABLE KEYS */;
INSERT INTO `upload_files` VALUES (19,NULL,_binary '\0',NULL,NULL,NULL,'products/29/1766346918770','https://res.cloudinary.com/dzxthw4yr/image/upload/v1766346920/products/29/1766346918770.jpg',60991,NULL,NULL,0,NULL,29),(20,NULL,_binary '\0',NULL,NULL,NULL,'products/29/1766346920670','https://res.cloudinary.com/dzxthw4yr/image/upload/v1766346922/products/29/1766346920670.jpg',157326,NULL,NULL,0,NULL,29),(21,NULL,_binary '\0',NULL,NULL,NULL,'products/29/1766346922885','https://res.cloudinary.com/dzxthw4yr/image/upload/v1766346924/products/29/1766346922885.jpg',78493,NULL,NULL,0,NULL,29);
/*!40000 ALTER TABLE `upload_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `users_chk_1` CHECK ((`status` between 0 and 1))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (4,NULL,_binary '\0',NULL,NULL,NULL,'ZHGAAG8E','uchihabigio@gmail.com','Tran Quoc Thai','$2a$10$SD4v3KPwd5DFTNbhpUvOdOzq8UeOLd.NXtc4KIx.3T401h4IWEZMK','0982957084',6,1),(5,NULL,_binary '\0',NULL,NULL,NULL,'VP0FW3UE','wearingarmor12345@gmail.com','Trinh Huu Hung','$2a$10$pYuFaK5qqHK./Nhr2ZFpu.I4IzVx7FagGSbqenDGX0vig70NKHyzO','0867276214',7,1),(6,NULL,_binary '\0',NULL,NULL,NULL,'7Z9VD917','trinhhuuhung92@gmail.com','Trinh Huu Hung','$2a$10$7AGw3PUb8nCoTTTe2jxW.uw51pg9Naspl1gl2EpwZdeAnlEcAV0XC','0123456189',8,1),(7,NULL,_binary '\0',NULL,NULL,NULL,'JET48BIL','trinhhuuhung92@gmail.com','Trinh Huu Hung A','$2a$10$lqiVMVgiEoxvVHs351HGnugTrGsLRA68UTzufe0B2TiIippTvBh52','0567456783',9,1);
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

-- Dump completed on 2025-12-22 17:33:17
