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

 Date: 29/05/2019 13:43:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator_id` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  `role_index` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `show_users` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` varchar(255) DEFAULT NULL,
  `sys_flag` varchar(255) DEFAULT NULL COMMENT '1为登录后跳后台用户',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('5fe20a89-e050-11e6-9dcb-6c92bf25b1f3', '2017-01-22 11:26:26', 'admin', 1, '', '统一申办管理员', 3, '管理员', 0, '2017-01-22 11:27:16', '', '1');
INSERT INTO `role` VALUES ('69875db7-f81c-11e6-9dcb-6c92bf25b1f3', '2017-02-21 17:59:19', 'admin', 1, '', '统一申办用户', 4, '普通用户', 0, '2017-02-21 17:59:19', '', '1');
INSERT INTO `role` VALUES ('b2ceb334-a6e4-11e6-9dcb-6c92bf25b1f3', '2016-11-10 09:26:26', 'admin', 1, '', '管理员', 1, '超级管理员', 0, '2016-11-10 09:27:16', '', '1');
INSERT INTO `role` VALUES ('c5906e10-2ee6-11e7-9dcb-6c92bf25b1f3', '2017-05-02 11:23:55', 'admin1', 1, '', '信息编辑人员', 5, '普通用户', 0, '2017-05-02 11:23:55', '', '1');
INSERT INTO `role` VALUES ('d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3', '2016-11-10 09:27:16', 'admin', 1, '', '普通用户', 2, '普通用户', 0, '2016-11-10 09:27:16', '', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
