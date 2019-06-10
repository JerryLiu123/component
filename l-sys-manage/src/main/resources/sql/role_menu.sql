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

 Date: 29/05/2019 13:43:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` varchar(128) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
INSERT INTO `role_menu` VALUES ('5fe20a89-e050-11e6-9dcb-6c92bf25b1f3_5', '5fe20a89-e050-11e6-9dcb-6c92bf25b1f3', '5');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_3', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '3');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_5', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '5');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_6', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '6');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_8', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '8');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_805dc6b8bc8b4ae28ea229200b437dd6', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '805dc6b8bc8b4ae28ea229200b437dd6');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_924f9a36c2aa455d9ea9e86a27e21ae8', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '924f9a36c2aa455d9ea9e86a27e21ae8');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_a0d97ebe04924741ae467c7bd5de6e92', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'a0d97ebe04924741ae467c7bd5de6e92');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_abf7b2de5cd64bc7bc4860eb6d03fd0b', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'abf7b2de5cd64bc7bc4860eb6d03fd0b');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_b3f3e6a4e71741a7ad9f0989ff0175a6', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'b3f3e6a4e71741a7ad9f0989ff0175a6');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_c06fed08bb564f1eb0d318a567b4c46e', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'c06fed08bb564f1eb0d318a567b4c46e');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_c0eb74c02eb74f1292a0b1bcbcc20748', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'c0eb74c02eb74f1292a0b1bcbcc20748');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_e0f447c5491f45fe853c96e70332b7ec', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'e0f447c5491f45fe853c96e70332b7ec');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_e3179f01be9a4bbd9745dfb463752a12', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'e3179f01be9a4bbd9745dfb463752a12');
INSERT INTO `role_menu` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3_e9665d25069b4da8b7063ef2a9fec59a', 'b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', 'e9665d25069b4da8b7063ef2a9fec59a');
INSERT INTO `role_menu` VALUES ('c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3_5', 'c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3', '5');
INSERT INTO `role_menu` VALUES ('c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3_a0d97ebe04924741ae467c7bd5de6e92', 'c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3', 'a0d97ebe04924741ae467c7bd5de6e92');
INSERT INTO `role_menu` VALUES ('c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3_c06fed08bb564f1eb0d318a567b4c46e', 'c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3', 'c06fed08bb564f1eb0d318a567b4c46e');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
