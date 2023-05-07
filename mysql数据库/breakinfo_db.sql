/*
Navicat MySQL Data Transfer

Source Server         : mysql5.6
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : breakinfo_db

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2020-04-28 12:40:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_areainfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_areainfo`;
CREATE TABLE `t_areainfo` (
  `areaId` int(11) NOT NULL AUTO_INCREMENT COMMENT '区域编号',
  `areaName` varchar(20) NOT NULL COMMENT '区域名称',
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_areainfo
-- ----------------------------
INSERT INTO `t_areainfo` VALUES ('1', '本部');
INSERT INTO `t_areainfo` VALUES ('2', '南校区');
INSERT INTO `t_areainfo` VALUES ('3', '北校区');

-- ----------------------------
-- Table structure for `t_breakinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_breakinfo`;
CREATE TABLE `t_breakinfo` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `breakTypeObj` int(11) NOT NULL COMMENT '故障类型',
  `buildingObj` int(11) NOT NULL COMMENT '所在宿舍',
  `breakPhoto` varchar(60) NOT NULL COMMENT '故障图片',
  `breakReason` varchar(500) NOT NULL COMMENT '故障信息',
  `studentObj` varchar(30) NOT NULL COMMENT '申报学生',
  `commitDate` varchar(20) DEFAULT NULL COMMENT '报修时间',
  `taskStateObj` int(11) NOT NULL COMMENT '任务状态',
  `repariManObj` varchar(20) NOT NULL COMMENT '维修人员',
  `handResult` varchar(500) DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`taskId`),
  KEY `breakTypeObj` (`breakTypeObj`),
  KEY `buildingObj` (`buildingObj`),
  KEY `studentObj` (`studentObj`),
  KEY `taskStateObj` (`taskStateObj`),
  KEY `repariManObj` (`repariManObj`),
  CONSTRAINT `t_breakinfo_ibfk_1` FOREIGN KEY (`breakTypeObj`) REFERENCES `t_breaktype` (`breakTypeId`),
  CONSTRAINT `t_breakinfo_ibfk_2` FOREIGN KEY (`buildingObj`) REFERENCES `t_buildinginfo` (`buildingId`),
  CONSTRAINT `t_breakinfo_ibfk_3` FOREIGN KEY (`studentObj`) REFERENCES `t_userinfo` (`user_name`),
  CONSTRAINT `t_breakinfo_ibfk_4` FOREIGN KEY (`taskStateObj`) REFERENCES `t_taskinfostate` (`stateId`),
  CONSTRAINT `t_breakinfo_ibfk_5` FOREIGN KEY (`repariManObj`) REFERENCES `t_repairman` (`repainManNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_breakinfo
-- ----------------------------
INSERT INTO `t_breakinfo` VALUES ('1', '1', '1', 'upload/11e35e04-81d3-4ffc-857e-89cd20ef7a0e.jpg', '厕所漏水了，上厕所一点不方便啊啊！', '13688886666', '2020-04-27 23:55:23', '1', '00000', '--');
INSERT INTO `t_breakinfo` VALUES ('2', '1', '1', 'upload/NoImage.jpg', 'bbbb', '13688886666', '2020-04-28 00:07:52', '1', '00000', '--');
INSERT INTO `t_breakinfo` VALUES ('3', '1', '1', 'upload/NoImage.jpg', 'ccccc', '13688886666', '2020-04-28 00:08:55', '1', '00000', '--');
INSERT INTO `t_breakinfo` VALUES ('4', '1', '1', 'upload/dbf855e2-63c9-4d27-a33a-4363ad1ca920.jpg', '寝室的灯泡坏了，很暗淡！', '13866668888', '2020-04-28 00:12:06', '1', '00000', '--');
INSERT INTO `t_breakinfo` VALUES ('5', '2', '2', 'upload/88757794-fe45-4ee4-a2bf-38ae9c041d02.jpg', '寝室的门锁坏了，经常打开门卡半天，烦死了！', '13866668888', '2020-04-28 12:07:03', '2', 'RY001', '已经安排维修人员来了');

-- ----------------------------
-- Table structure for `t_breaktype`
-- ----------------------------
DROP TABLE IF EXISTS `t_breaktype`;
CREATE TABLE `t_breaktype` (
  `breakTypeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型编号',
  `breakTypeName` varchar(20) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`breakTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_breaktype
-- ----------------------------
INSERT INTO `t_breaktype` VALUES ('1', '水电问题');
INSERT INTO `t_breaktype` VALUES ('2', '门窗问题');

-- ----------------------------
-- Table structure for `t_buildinginfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_buildinginfo`;
CREATE TABLE `t_buildinginfo` (
  `buildingId` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `areaObj` int(11) NOT NULL COMMENT '所在区域',
  `buildingName` varchar(20) NOT NULL COMMENT '宿舍名称',
  `manageMan` varchar(20) DEFAULT NULL COMMENT '管理员',
  `telephone` varchar(20) DEFAULT NULL COMMENT '门卫电话',
  PRIMARY KEY (`buildingId`),
  KEY `areaObj` (`areaObj`),
  CONSTRAINT `t_buildinginfo_ibfk_1` FOREIGN KEY (`areaObj`) REFERENCES `t_areainfo` (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_buildinginfo
-- ----------------------------
INSERT INTO `t_buildinginfo` VALUES ('1', '1', '芙蓉8-224', '王曦彤', '028-83921234');
INSERT INTO `t_buildinginfo` VALUES ('2', '1', '芙蓉5-227', '李晓燕', '028-82980123');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) DEFAULT NULL COMMENT '留言时间',
  `replyContent` varchar(1000) DEFAULT NULL COMMENT '管理回复',
  `replyTime` varchar(20) DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '这个系统不错', '我可以解决我的问题', '13866668888', '2020-04-27 23:32:01', '对对对', '2020-04-27 23:32:03');
INSERT INTO `t_leaveword` VALUES ('2', '你好都可以解决啥问题', '我们宿舍的东西坏了，都可以上报吗？', '13866668888', '2020-04-28 00:18:31', '是的都是可以的，我们会派人来维修', '2020-04-28 11:57:11');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(800) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '小程序报修平台上线', '以后同学们宿舍有是问题可以上报！', '2020-04-27 23:32:17');
INSERT INTO `t_notice` VALUES ('2', '大家好欢迎上报', '各种宿舍问题解决', '2020-04-28 11:59:04');

-- ----------------------------
-- Table structure for `t_repairman`
-- ----------------------------
DROP TABLE IF EXISTS `t_repairman`;
CREATE TABLE `t_repairman` (
  `repainManNumber` varchar(20) NOT NULL COMMENT 'repainManNumber',
  `repairManName` varchar(20) NOT NULL COMMENT '人员姓名',
  `repairManSex` varchar(4) NOT NULL COMMENT '性别',
  `myPhoto` varchar(60) NOT NULL COMMENT '个人头像',
  `workYear` varchar(20) NOT NULL COMMENT '工作年限',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `repairMan` varchar(800) DEFAULT NULL COMMENT '维修人员介绍',
  PRIMARY KEY (`repainManNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_repairman
-- ----------------------------
INSERT INTO `t_repairman` VALUES ('00000', '--', '--', 'upload/NoImage.jpg', '--', '--', '--');
INSERT INTO `t_repairman` VALUES ('RY001', '王天天', '男', 'upload/7d0e582b-bfb8-41f9-9ac4-8192a6ac441d.jpg', '3年', '13080825034', '水电工一名，精通各种水电技术，老师傅了，技术好！');
INSERT INTO `t_repairman` VALUES ('RY002', '李明堂', '男', 'upload/cc8913f7-557e-4d14-bda1-9f598f64033e.jpg', '2年', '13850813108', '各种家具维修，门窗安装');

-- ----------------------------
-- Table structure for `t_taskinfostate`
-- ----------------------------
DROP TABLE IF EXISTS `t_taskinfostate`;
CREATE TABLE `t_taskinfostate` (
  `stateId` int(11) NOT NULL AUTO_INCREMENT COMMENT '状态编号',
  `stateName` varchar(20) NOT NULL COMMENT '状态名称',
  PRIMARY KEY (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_taskinfostate
-- ----------------------------
INSERT INTO `t_taskinfostate` VALUES ('1', '待处理');
INSERT INTO `t_taskinfostate` VALUES ('2', '处理中');
INSERT INTO `t_taskinfostate` VALUES ('3', '处理完毕');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `buildingObj` int(11) NOT NULL COMMENT '所在宿舍',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  `openid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  KEY `buildingObj` (`buildingObj`),
  CONSTRAINT `t_userinfo_ibfk_1` FOREIGN KEY (`buildingObj`) REFERENCES `t_buildinginfo` (`buildingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('13688886666', '--', '亮亮', '男', '1', '2020-01-01', 'upload/72b101b9-73cf-4f8d-8cd0-e74973d3b2e3.jpg', '--', '--', '--', '2020-04-27 23:48:11', 'oM7Mu5XyeVJSc8roaUCRlcz_IP9l');
INSERT INTO `t_userinfo` VALUES ('13866668888', '--', '鼠鼠', '男', '1', '2020-01-01', 'upload/ce007c9d-fe4d-4213-a1c2-f4f114891acb.jpg', '13818891234', 'test@126.com', '四川成都红星路', '2020-04-28 00:10:11', 'oM7Mu5XyeVJSc8roaUCRlcz_IP9k');
INSERT INTO `t_userinfo` VALUES ('13980083423', '123', '王强', '男', '2', '2020-04-01', 'upload/346a6354-deae-4376-a94f-eefe527f4d71.jpg', '13080852083', 'wagnqiang@163.com', '南充滨江路12号', '2020-04-28 23:31:33', null);
