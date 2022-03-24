/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : upload_sys

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 25/11/2021 22:33:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `sequence` int(11) DEFAULT '0',
  `icon` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `pcode` varchar(255) DEFAULT NULL COMMENT '父菜单',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1-菜单 0-不是菜单',
  `enabled` tinyint(4) NOT NULL,
  `displayed` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` VALUES (1, '用户管理', '/user/list', 'user', 1, '', NULL, 'system', 1, 1, 1);
INSERT INTO `menu` VALUES (2, '菜单管理', '/menu/list', 'menu', 3, '', NULL, 'system', 1, 1, 1);
INSERT INTO `menu` VALUES (3, '角色管理', '/role/list', 'role', 2, NULL, NULL, 'system', 1, 1, 1);
INSERT INTO `menu` VALUES (7, '系统设置', '#', 'system', 13, 'fa-cog', NULL, '', 1, 1, 1);
INSERT INTO `menu` VALUES (99, '删除', '/user/delete', 'user_delete', 2, NULL, NULL, 'user', 0, 1, 1);
INSERT INTO `menu` VALUES (100, '授权', '/user/role', 'user_permission', 0, NULL, NULL, 'user', 0, 1, 1);
INSERT INTO `menu` VALUES (101, '编辑', '/user/update', 'user_edit', 1, NULL, NULL, 'user', 0, 1, 1);
INSERT INTO `menu` VALUES (102, '新增', '/user/insert', 'user_create', 3, NULL, NULL, 'user', 0, 1, 1);
INSERT INTO `menu` VALUES (104, '新增', '/role/insert', 'role_create', 0, NULL, NULL, 'role', 0, 1, 1);
INSERT INTO `menu` VALUES (105, '分配权限', '/role/permission', 'role_permission', 1, NULL, NULL, 'role', 0, 1, 1);
INSERT INTO `menu` VALUES (106, '编辑', '/role/get', 'role_update', 2, NULL, NULL, 'role', 0, 1, 1);
INSERT INTO `menu` VALUES (107, '删除', '/role/delete', 'role_delete', 3, NULL, NULL, 'role', 0, 1, 1);
INSERT INTO `menu` VALUES (108, '新增', '/menu/insert', 'menu_create', 0, NULL, NULL, 'menu', 0, 1, 1);
INSERT INTO `menu` VALUES (109, '编辑', '/menu/get', 'menu_edit', 1, NULL, NULL, 'menu', 0, 1, 1);
INSERT INTO `menu` VALUES (110, '删除', '/menu/delete', 'menu_delete', 2, NULL, NULL, 'menu', 0, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `role_key` varchar(20) NOT NULL,
  `enabled` int(1) NOT NULL COMMENT '1可用  0不可用',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `enable` tinyint(4) DEFAULT NULL COMMENT '是否可用：0-不可用 1-可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'cjadmin', '$2a$10$4F/L8bThSntZtqed5dS/q.KyucdeOXtr2VEfhNhn3HfE9QIOwaUQm', '超级管理员', 1);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 8);
INSERT INTO `user_role` VALUES (4, 15, 1);
INSERT INTO `user_role` VALUES (5, 16, 2);
COMMIT;


CREATE TABLE `qczj` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `city_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `brand_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `brand_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `car_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `car_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `car_series_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `car_series_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `km` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `firstregtime` datetime DEFAULT NULL,
  `uid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of qczj
-- ----------------------------
BEGIN;
INSERT INTO `qczj` VALUES ('1', '370100', '日照', '山东', '13000000000', '0', '现代', '0', '伊兰特', '0', NULL, '0', NULL, '0', '2021-11-29 13:07:29', NULL);
INSERT INTO `qczj` VALUES ('2', '370101', '德州', '山东', '15000000000', '0', '大众', '1', 'cc', '0', NULL, '0', NULL, NULL, '2021-11-29 13:12:07', NULL);
COMMIT;

 UPDATE `upload_sys`.`menu` SET `name` = '汽车之家', `url` = '#', `code` = 'zj', `sequence` = 0, `icon` = NULL, `description` = NULL, `pcode` = NULL, `type` = 1, `enabled` = 1, `displayed` = 1 WHERE `id` = 111;
 INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (112, '之家-录音', '/upload/qczj/vedio/list', 'vedio', 1, NULL, NULL, 'zj', 1, 1, 1);
 INSERT INTO `upload_sys`.`menu` (`id`, `name`, `url`, `code`, `sequence`, `icon`, `description`, `pcode`, `type`, `enabled`, `displayed`) VALUES (113, '之家-数据上传', '/upload/qczj/list', 'qczj', 0, NULL, NULL, 'zj', 1, 1, 1);