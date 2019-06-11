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

 Date: 11/06/2019 10:05:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_login_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_info`;
CREATE TABLE `sys_login_info` (
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

-- ----------------------------
-- Table structure for sys_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_info`;
CREATE TABLE `sys_menu_info` (
  `menu_id` varchar(50) NOT NULL,
  `menu_name` varchar(50) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `request_url` varchar(50) DEFAULT NULL,
  `menu_icon` varchar(50) DEFAULT NULL,
  `state` varchar(5) DEFAULT NULL,
  `insert_time` varchar(30) DEFAULT NULL,
  `order_id` varchar(10) DEFAULT NULL,
  `menu_img_path` varchar(50) DEFAULT NULL COMMENT '菜单图片地址',
  `isparent` varchar(50) DEFAULT NULL COMMENT '是否为父节点',
  `level` varchar(50) DEFAULT NULL COMMENT '菜单等级',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `request_url` (`request_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_info`;
CREATE TABLE `sys_role_info` (
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
-- Table structure for sys_role_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_login`;
CREATE TABLE `sys_role_login` (
  `id` varchar(50) NOT NULL,
  `login_id` varchar(50) DEFAULT NULL COMMENT '登陆用户表ID',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `role_id_index` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(128) NOT NULL,
  `role_id` varchar(50) DEFAULT NULL,
  `menu_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
