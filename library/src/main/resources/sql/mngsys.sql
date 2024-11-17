/*
 Navicat Premium Dump SQL

 Source Server         : Library
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : mngsys

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 14/11/2024 11:53:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `Bid` int NOT NULL,
  `Btitle` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Bauthor` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Bversion` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`Bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (0, 'Manifesto of Communist', 'Karl Marx', '1st Edition');
INSERT INTO `book` VALUES (1, 'Nation and Revolution', 'Vladimir Leinin', '9th Edition');
INSERT INTO `book` VALUES (101, 'Effective Java', 'Joshua Bloch', '3rd Edition');

-- ----------------------------
-- Table structure for bookinfo
-- ----------------------------
DROP TABLE IF EXISTS `bookinfo`;
CREATE TABLE `bookinfo`  (
  `Bid` int NOT NULL,
  `Bname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `Bauthor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `Bversion` int NOT NULL,
  PRIMARY KEY (`Bid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookinfo
-- ----------------------------
INSERT INTO `bookinfo` VALUES (1, 'The Communist Manifesto', 'Karl Marx', 1848);
INSERT INTO `bookinfo` VALUES (2, 'Country and Revolution', 'Vladimir Lenin', 1917);
INSERT INTO `bookinfo` VALUES (3, 'The selection of Mao Zedong', 'Mao Zedong', 1977);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int NOT NULL,
  `Sid` int NULL DEFAULT NULL,
  `Bid` int NULL DEFAULT NULL,
  `borrowDate` int NULL DEFAULT NULL,
  `returnDate` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (0, 3, 103, 241113, 241114);
INSERT INTO `borrow` VALUES (200, 2, 102, 20241130, 0);

-- ----------------------------
-- Table structure for borrowinfo
-- ----------------------------
DROP TABLE IF EXISTS `borrowinfo`;
CREATE TABLE `borrowinfo`  (
  `Sid` int NOT NULL,
  `Bid` int NOT NULL,
  `Bdate` int NULL DEFAULT NULL,
  `Rdate` int NULL DEFAULT NULL,
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowinfo
-- ----------------------------

-- ----------------------------
-- Table structure for returninfo
-- ----------------------------
DROP TABLE IF EXISTS `returninfo`;
CREATE TABLE `returninfo`  (
  `Sid` int NOT NULL,
  `Bid` int NOT NULL,
  `Bdate` int NULL DEFAULT NULL,
  `Rdate` int NULL DEFAULT NULL,
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of returninfo
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `Sid` int NOT NULL,
  `Sname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Sdept` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Smajor` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `Sborrowed` int NULL DEFAULT NULL,
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (2, 'Xjw', 'Informatik', 'Industrial Intelligence', 1);
INSERT INTO `student` VALUES (95001, 'San Zhang', 'Informatics', 'Industrial Intelligence', 0);
INSERT INTO `student` VALUES (95002, 'Li Si', 'Computer Science', 'Software Engineering', 0);
INSERT INTO `student` VALUES (95003, 'Wang Wu', 'Mathematics', 'Statistics', 1);

-- ----------------------------
-- Table structure for stuinfo
-- ----------------------------
DROP TABLE IF EXISTS `stuinfo`;
CREATE TABLE `stuinfo`  (
  `Sid` int NOT NULL AUTO_INCREMENT,
  `Sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `Sdept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `Smajor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `Shas_borrow` int NOT NULL,
  PRIMARY KEY (`Sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stuinfo
-- ----------------------------
INSERT INTO `stuinfo` VALUES (1, 'X Jw', 'Info', 'Industrial Intelligence', 0);
INSERT INTO `stuinfo` VALUES (2, 'Z Zy', 'Info', 'Industrial Intelligence', 0);
INSERT INTO `stuinfo` VALUES (3, 'C Jy', 'Matr', 'Future tech', 0);

SET FOREIGN_KEY_CHECKS = 1;
