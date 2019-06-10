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

 Date: 29/05/2019 13:43:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
BEGIN;
INSERT INTO `userinfo` VALUES ('12', '2018-11-15 11:06:38', NULL, 1, 'admin', '320c4b482db0b6e123e28270e6109783', NULL, '1', NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
