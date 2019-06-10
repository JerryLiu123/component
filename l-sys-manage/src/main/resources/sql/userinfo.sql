/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : sys_manage

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 10/06/2019 15:36:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userinfo_copy1
-- ----------------------------
DROP TABLE IF EXISTS `userinfo_copy1`;
CREATE TABLE `userinfo_copy1` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator_id` varchar(255) DEFAULT NULL,
  `error_num` int(11) DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` varchar(255) DEFAULT NULL,
  `update_pwd_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
