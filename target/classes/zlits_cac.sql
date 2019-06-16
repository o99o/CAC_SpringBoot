DROP DATABASE IF EXISTS zlits_cac;

CREATE DATABASE zlits_cac;

USE zlits_cac;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志',
  `userName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作人',
  `createTime` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '时间',
  `content` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '详细',
  `operation` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作类型（增删改）',
  `ip` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'IP地址',
  `module` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '所属模块',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='操作日志记录';


-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menuId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menuName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `menuUrl` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '方法(菜单地址)',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父ID',
  `menuDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `state` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单状态/OPEN/CLOSED',
  `iconCls` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  `seq` bigint(20) DEFAULT NULL COMMENT '顺序排序',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', 'CAC平台', '', '-1', '主菜单', 'opend', 'icon-home', '1');
INSERT INTO `menu` VALUES ('2', '系统管理', '', '1', '系统管理', 'closed', 'safe_manage.png', '1');
INSERT INTO `menu` VALUES ('27', '用户管理', 'cacUser/cacUserIndex', '2', '', 'open', 'icon-192', '2');
INSERT INTO `menu` VALUES ('32', '角色管理', 'cacRole/cacRoleIndex', '2', '', 'open', 'icon-196', '3');
INSERT INTO `menu` VALUES ('33', '菜单管理', 'cacMenu/cacMenuIndex', '2', '', 'open', 'icon-199', '4');
INSERT INTO `menu` VALUES ('34', '日志管理', 'cacLog/cacLogIndex', '2', '', 'open', 'icon-193', '5');
INSERT INTO `menu` VALUES ('51', '修改密码', 'cacInto/intoUpdatePassword', '2', '修改密码（layui）', 'open', '', '6');

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `operationId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '具体的方法',
  `operationName` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '方法名',
  `menuId` bigint(20) DEFAULT NULL COMMENT '所属菜单',
  `menuName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`operationId`),
  KEY `menuId` (`menuId`),
  CONSTRAINT `operation_ibfk_1` FOREIGN KEY (`menuId`) REFERENCES `menu` (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=10036 DEFAULT CHARSET=utf8 COMMENT='具体的页面按钮上的方法\r\n（此自增ID至少从10000开始）';

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES ('10019', '新增', '27', '用户');
INSERT INTO `operation` VALUES ('10020', '修改', '27', '用户');
INSERT INTO `operation` VALUES ('10021', '删除', '27', '用户');
INSERT INTO `operation` VALUES ('10022', '新增', '32', '角色管理');
INSERT INTO `operation` VALUES ('10024', '修改', '32', '角色管理');
INSERT INTO `operation` VALUES ('10025', '删除', '32', '角色管理');
INSERT INTO `operation` VALUES ('10027', '授权', '32', '角色管理');
INSERT INTO `operation` VALUES ('10028', '新增', '33', '菜单管理');
INSERT INTO `operation` VALUES ('10029', '修改', '33', '菜单管理');
INSERT INTO `operation` VALUES ('10030', '删除', '33', '菜单管理');
INSERT INTO `operation` VALUES ('10031', '按钮管理', '33', '菜单管理');
INSERT INTO `operation` VALUES ('10032', '新增', '1', '某系统');
INSERT INTO `operation` VALUES ('10033', '下载后台日志（log4j）', '34', '日志管理');
INSERT INTO `operation` VALUES ('10035', '删除', '34', '日志管理');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `menuIds` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '菜单IDs',
  `operationIds` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '按钮IDS',
  `roleDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '33,34,-1,27,1,2,51,32', '10020,10031,10030,10019,10029,10022,10033,10021,10024,10035,10025,10028,10027', '拥有全部权限的超级管理员角色,很厉害的！真的吗');
INSERT INTO `role` VALUES ('4', '普通管理员', ' ', '10003,10004,10011', '使用方中拥有最高权限的角色');

-- ----------------------------
-- Table structure for token
-- ----------------------------
DROP TABLE IF EXISTS `token`;
CREATE TABLE `token` (
  `tokenId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userAgent` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户（md5）',
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5(username+md5(useragent))',
  `createTime` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `expireTime` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`tokenId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录信息（用于自动登录）';

-- ----------------------------
-- Records of token
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `userType` tinyint(4) DEFAULT NULL COMMENT '用户类型',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `userDescription` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`userId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'super', 'super', '1', '1', '超级管理员，供开发方使用');
INSERT INTO `user` VALUES ('47', 'admin', 'admin', '2', '4', '普通管理员');

