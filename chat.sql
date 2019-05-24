/*
 Navicat Premium Data Transfer

 Source Server         : syr-tengxun-server
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 140.143.123.76
 Source Database       : chat

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 05/24/2019 18:16:51 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` bigint(20) NOT NULL,
  `from_user_id` bigint(20) NOT NULL,
  `to_user_id` bigint(20) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 申请中 1 已同意 2 已拒绝',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `from_user_name` varchar(255) NOT NULL,
  `to_user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING HASH,
  UNIQUE KEY `from_user_id` (`from_user_id`,`to_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `friend`
-- ----------------------------
BEGIN;
INSERT INTO `friend` VALUES ('1124974583431041025', '10', '9', '1', '2019-05-05 17:50:16', 'syr', 'shangyouren'), ('1126902058590990337', '10', '10', '0', '2019-05-11 01:29:21', 'syr', 'syr');
COMMIT;

-- ----------------------------
--  Table structure for `groups`
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 正常 1 已解散',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `groups`
-- ----------------------------
BEGIN;
INSERT INTO `groups` VALUES ('1125011061720764417', 'syr的群组', '2019-05-05 20:15:13', '0');
COMMIT;

-- ----------------------------
--  Table structure for `message`
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` bigint(20) NOT NULL,
  `to_user_id` bigint(20) DEFAULT NULL,
  `to_group_id` bigint(20) DEFAULT NULL,
  `type` tinyint(2) NOT NULL COMMENT '1 发送给个人 2 发送给群',
  `message` varchar(255) NOT NULL,
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 正常 1 实效',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '1 文本消息  2 url消息',
  `from_user_name` varchar(255) NOT NULL,
  `to_user_name` varchar(255) DEFAULT NULL,
  `to_group_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `from_user_id` (`from_user_id`),
  KEY `to_user_id` (`to_user_id`),
  KEY `to_group_id` (`to_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1126901675068092419 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `message`
-- ----------------------------
BEGIN;
INSERT INTO `message` VALUES ('1124974663823384578', '9', '10', null, '1', '123', '2019-05-05 17:50:35', '0', '2019-05-05 17:50:35', '1', 'shangyouren', 'syr', null), ('1124974671658344449', '9', '10', null, '1', '123', '2019-05-05 17:50:37', '0', '2019-05-05 17:50:37', '1', 'shangyouren', 'syr', null), ('1124975459956809729', '10', '9', null, '1', '123', '2019-05-05 17:53:45', '0', '2019-05-05 17:53:45', '1', 'syr', 'shangyouren', null), ('1124975474922086401', '10', '9', null, '1', '1235', '2019-05-05 17:53:48', '0', '2019-05-05 17:53:48', '1', 'syr', 'shangyouren', null), ('1124975568153075714', '9', '10', null, '1', '1235', '2019-05-05 17:54:11', '0', '2019-05-05 17:54:11', '1', 'shangyouren', 'syr', null), ('1124975581100892161', '9', '10', null, '1', '1235', '2019-05-05 17:54:14', '0', '2019-05-05 17:54:14', '1', 'shangyouren', 'syr', null), ('1124976297571901441', '10', '9', null, '1', '123', '2019-05-05 17:57:05', '0', '2019-05-05 17:57:05', '1', 'syr', 'shangyouren', null), ('1124976325493383169', '10', '9', null, '1', '123', '2019-05-05 17:57:11', '0', '2019-05-05 17:57:11', '1', 'syr', 'shangyouren', null), ('1124986951359913986', '10', '9', null, '1', '123435345435', '2019-05-05 18:39:25', '0', '2019-05-05 18:39:25', '1', 'syr', 'shangyouren', null), ('1124988125974749185', '10', '9', null, '1', '12321312321', '2019-05-05 18:44:05', '0', '2019-05-05 18:44:05', '1', 'syr', 'shangyouren', null), ('1124990282430013441', '10', '9', null, '1', '123435345435', '2019-05-05 18:52:39', '0', '2019-05-05 18:52:39', '1', 'syr', 'shangyouren', null), ('1124990290617294849', '10', '9', null, '1', '123435345435', '2019-05-05 18:52:41', '0', '2019-05-05 18:52:41', '1', 'syr', 'shangyouren', null), ('1124990295201669121', '10', '9', null, '1', '123435345435', '2019-05-05 18:52:42', '0', '2019-05-05 18:52:42', '1', 'syr', 'shangyouren', null), ('1124990300444549122', '10', '9', null, '1', '123435345435', '2019-05-05 18:52:43', '0', '2019-05-05 18:52:43', '1', 'syr', 'shangyouren', null), ('1125011731333111809', '9', null, '1125011061720764417', '2', '12344dfgfd', '2019-05-05 20:17:53', '0', '2019-05-05 20:17:53', '1', 'shangyouren', null, 'syr的群组'), ('1125021096836337666', '10', '9', null, '1', 'sldkfj', '2019-05-05 20:55:06', '0', '2019-05-05 20:55:06', '1', 'syr', 'shangyouren', null), ('1125021114813124609', '10', '9', null, '1', 'sldkfj', '2019-05-05 20:55:10', '0', '2019-05-05 20:55:10', '1', 'syr', 'shangyouren', null), ('1125021136933883906', '10', '9', null, '1', 'sldkfj', '2019-05-05 20:55:15', '0', '2019-05-05 20:55:15', '1', 'syr', 'shangyouren', null), ('1126901596261314562', '10', '9', null, '1', '12332423423', '2019-05-11 01:27:31', '0', '2019-05-11 01:27:31', '1', 'syr', 'shangyouren', null), ('1126901675068092418', '10', null, '1125011061720764417', '2', '12332423423', '2019-05-11 01:27:50', '0', '2019-05-11 01:27:50', '1', 'syr', null, 'syr的群组');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `lev` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1 群主 2 管理员  3  普通成员',
  `desc_lev` varchar(255) DEFAULT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 正常',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `lev` (`lev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', '1', '群主', '0', '2019-05-05 20:14:25'), ('2', '2', '管理员', '0', '2019-05-05 20:14:33'), ('3', '3', '普通成员', '0', '2019-05-05 20:14:48');
COMMIT;

-- ----------------------------
--  Table structure for `user_group`
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT '0',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 申请 1 已通过  2 已拒绝 3 已推出',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `user_group`
-- ----------------------------
BEGIN;
INSERT INTO `user_group` VALUES ('1125011061922091009', '10', '1125011061720764417', '1', '1', '2019-05-05 20:15:13'), ('1125011343724793858', '9', '1125011061720764417', '3', '1', '2019-05-05 20:16:20');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('9', 'shangyouren', '202cb962ac59075b964b07152d234b70', null, '2019-05-05 17:45:48', '0'), ('10', 'syr', '202cb962ac59075b964b07152d234b70', null, '2019-05-05 17:50:00', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
