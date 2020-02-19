/*
 Navicat Premium Data Transfer

 Source Server         : hutter
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : source_item_one

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 19/02/2020 20:22:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_permission`;
CREATE TABLE `base_admin_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `pid` int(11) NOT NULL COMMENT '父菜单id',
  `descpt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` int(1) NOT NULL DEFAULT 1 COMMENT '删除标志（0:删除 1：存在）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_admin_permission
-- ----------------------------
INSERT INTO `base_admin_permission` VALUES (1, '系统管理', 0, '系统管理', NULL, '2020-01-29 21:27:26', '2020-01-29 21:27:26', 1);
INSERT INTO `base_admin_permission` VALUES (2, '账号管理', 1, '账号管理', '/adminUser/adminUserManage', '2020-01-29 21:26:50', '2020-01-29 21:26:50', 1);
INSERT INTO `base_admin_permission` VALUES (3, '角色管理', 1, '角色管理', '/adminRole/adminRoleManage', '2020-02-02 16:19:57', '2020-02-02 16:19:57', 1);
INSERT INTO `base_admin_permission` VALUES (4, '权限管理', 1, '权限管理', '/adminPermission/adminPermissionManage', '2020-02-02 16:20:11', '2020-02-02 16:20:11', 1);
INSERT INTO `base_admin_permission` VALUES (5, '用户管理', 1, '用户管理', '/api/user/userManage', '2020-01-29 21:28:20', '2020-01-29 21:28:20', 1);

-- ----------------------------
-- Table structure for base_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_role`;
CREATE TABLE `base_admin_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  `permissions` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `role_status` int(1) NOT NULL DEFAULT 1 COMMENT '1：有效    0：无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_admin_role
-- ----------------------------
INSERT INTO `base_admin_role` VALUES (1, '超级管理员', '超级管理员', '1', '2020-01-22 15:59:51', '2020-01-22 15:59:54', 1);
INSERT INTO `base_admin_role` VALUES (2, '普通管理员', '普通管理员', '2,5', '2020-01-22 16:01:39', '2020-01-22 16:01:41', 1);
INSERT INTO `base_admin_role` VALUES (3, '客服人员', '客服人员', '5', '2020-01-22 16:02:21', '2020-01-22 16:02:23', 1);
INSERT INTO `base_admin_role` VALUES (28, 'test', 'test', '2,3', '2020-02-02 12:02:14', '2020-02-02 12:05:46', 1);

-- ----------------------------
-- Table structure for base_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_user`;
CREATE TABLE `base_admin_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户名称',
  `sys_user_pwd` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户密码',
  `role_id` int(255) NOT NULL COMMENT '角色',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `create_time` datetime(6) NOT NULL COMMENT '创建时间',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
  `user_status` int(1) NOT NULL DEFAULT 1 COMMENT '状态（1：无效；0：无效）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理员帐号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_admin_user
-- ----------------------------
INSERT INTO `base_admin_user` VALUES (1, 'admin', '928bfd2577490322a6e19b793691467e', 1, '15570069597', '2020-01-26 21:02:49.000000', '2020-01-26 21:02:51.000000', 1);
INSERT INTO `base_admin_user` VALUES (2, 'hutter', '86e59c39bdee4a2e6745f0e4ea55be69', 2, '15570069597', '2020-02-01 12:55:28.000000', NULL, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `user_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `user_status` tinyint(255) NOT NULL COMMENT '0：失效   1：有效',
  `modifier_id` int(11) NULL DEFAULT NULL COMMENT '修改人id',
  `modifier_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '诗仙', '928bfd2577490322a6e19b793691467e', '15570069597', '1015910794@qq.com', '2020-02-04 19:06:24', '2020-02-04 19:06:27', 1, 1, 'admin');
INSERT INTO `user` VALUES (16, '诗圣', '19200c176673a55eedb27c46231712a7', '15570069597', '1015910794@qq.com', '2020-02-05 09:31:02', NULL, 1, 1, 'admin');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` tinyint(255) NULL DEFAULT NULL COMMENT '性别',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄(0：女  1：男)',
  `profession` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `identity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `modifier_id` int(11) NOT NULL COMMENT '修改人id',
  `modifier_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 1, '李白', 1, 18, '诗人', '唐朝长安', '3308820011212666X', '2020-02-04 19:10:44', '2020-02-04 19:10:47', 1, 'admin');
INSERT INTO `user_info` VALUES (6, 16, '杜甫', 1, 18, '诗人', '唐朝', '33088119971212375X', '2020-02-05 09:31:02', '2020-02-05 10:28:10', 1, 'admin');

SET FOREIGN_KEY_CHECKS = 1;
