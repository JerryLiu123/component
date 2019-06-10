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

 Date: 29/05/2019 13:43:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menuinfo
-- ----------------------------
DROP TABLE IF EXISTS `menuinfo`;
CREATE TABLE `menuinfo` (
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
-- Records of menuinfo
-- ----------------------------
BEGIN;
INSERT INTO `menuinfo` VALUES ('3', '登陆', '2', '/api/v1/login', 'icon-text', '1', '2018-11-115 10:50:10', '1092', '/theme/sys/css/icons/user.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('5', '测试权限', '2', '/test/auto', 'icon-text', '1', '', '1096', '/theme/sys/css/icons/expand-all.gif', '0', '3');
INSERT INTO `menuinfo` VALUES ('6', '角色管理', '2', '/platform/sys/role_list', 'icon-text', '1', '2018-11-115 10:50:10', '1094', '/theme/sys/css/icons/group.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('7', '机构列表', '4', '/platform/sys/dept_iframe', 'icon-text', '1', '2018-11-115 10:50:10', '1092', '/theme/sys/css/icons/chart_org_inverted.png', '0', '4');
INSERT INTO `menuinfo` VALUES ('8', '机构_用户列表', '4', '/platform/sys/deptuser_iframe', 'icon-text', '1', '2018-11-115 10:50:10', '1093', '/theme/sys/css/icons/report_user.png', '0', '4');
INSERT INTO `menuinfo` VALUES ('91d5c7ac586a47c9be94752d5cde7c6e', '测试菜单', '11', '/platform/sys/getLXFS1', 'icon-text', '0', '', '13', '/platform/sys/getLXFSapplication_edit.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('a0d97ebe04924741ae467c7bd5de6e92', '联系方式1', '2', '/platform/sys/getLXFS2', 'icon-text', '1', '', '2', '/theme/sys/css/icons/application_edit.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('b3f3e6a4e71741a7ad9f0989ff0175a6', '联系方式2', '2', '/platform/sys/getLXFS3', 'icon-text', '0', '', '13', '/theme/sys/css/icons/application_edit.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('c06fed08bb564f1eb0d318a567b4c46e', '内容详细', '9', '/platform/cms/cms_iframe', 'icon-text', '1', '', '1', '/theme/sys/css/icons/newspaper.png', '0', NULL);
INSERT INTO `menuinfo` VALUES ('e0f447c5491f45fe853c96e70332b7ec', '添加目录', '9', '/platform/cms/cms_ml', 'icon-text', '1', '', '2', '/theme/sys/css/icons/chart_org_inverted.png', '0', '3');
INSERT INTO `menuinfo` VALUES ('e9665d25069b4da8b7063ef2a9fec59a', '联系方式3', '2', '/platform/sys/getLXFS4', 'icon-text', '0', '', '13', '/theme/sys/css/icons/application_edit.png', '0', '3');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
