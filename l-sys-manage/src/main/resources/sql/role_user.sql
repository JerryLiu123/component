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

 Date: 29/05/2019 13:43:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `role_id` varchar(50) DEFAULT NULL,
  `id` varchar(50) NOT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
BEGIN;
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '45bff57d-e051-11e6-9dcb-6c92bf25b1f3', '12');
INSERT INTO `role_user` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa436dd-a6e8-11e6-9dcb-6c92bf25b1f3', '12');
INSERT INTO `role_user` VALUES ('5fe20a89-e050-11e6-9dcb-6c92bf25b1f3', '6fa4384e-a6e8-11e6-9dcb-6c92bf25b1f3', '12');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa438e5-a6e8-11e6-9dcb-6c92bf25b1f3', '2');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa439b6-a6e8-11e6-9dcb-6c92bf25b1f3', '3');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43aaa-a6e8-11e6-9dcb-6c92bf25b1f3', '4');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43b86-a6e8-11e6-9dcb-6c92bf25b1f3', '5');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43c3d-a6e8-11e6-9dcb-6c92bf25b1f3', '6');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43cf4-a6e8-11e6-9dcb-6c92bf25b1f3', '7');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43da0-a6e8-11e6-9dcb-6c92bf25b1f3', '8');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '6fa43e72-a6e8-11e6-9dcb-6c92bf25b1f3', '9');
INSERT INTO `role_user` VALUES ('69875db7-f81c-11e6-9dcb-6c92bf25b1f3', '87f7d8a5-f81c-11e6-9dcb-6c92bf25b1f3', '13');
INSERT INTO `role_user` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', 'b42b792e-aa0c-11e6-9dcb-6c92bf25b1f3', '11');
INSERT INTO `role_user` VALUES ('c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3', 'e3ee3c30-2ee6-11e7-9dcb-6c92bf25b1f3', '29eb2b9f-2ee6-11e7-9dcb-6c92bf25b1f3');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
