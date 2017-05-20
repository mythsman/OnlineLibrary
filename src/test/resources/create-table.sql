DROP table IF EXISTS course;
CREATE TABLE `course` (
  `schoolName` varchar(20) NOT NULL,
  `collegeName` varchar(20) NOT NULL,
  `courseName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('苏州大学','计算机科学与技术学院','离散数学');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('苏州大学','计算机科学与技术学院','C++程序设计');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('苏州大学','电子信息学院','高等数学');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('苏州大学','电子信息学院','C语言程序设计');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('清华大学','计算机科学与技术学院','离散数学');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('清华大学','计算机科学与技术学院','C++程序设计');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('清华大学','电子信息学院','高等数学');
INSERT INTO `course`(schoolName, collegeName, courseName) VALUES ('清华大学','电子信息学院','C语言程序设计');

DROP table IF EXISTS `article`;
CREATE TABLE `article` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `school` varchar(20) NOT NULL,
  `college` varchar(20) NOT NULL,
  `course` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `brief` varchar(100) DEFAULT NULL,
  `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `download` int(11) DEFAULT '0',
  `price` int(11) DEFAULT '0',
  `hash` varchar(50) NOT NULL,
  PRIMARY KEY (`fid`),
  KEY `schoolIndex` (`school`),
  KEY `collegeIndex` (`college`),
  KEY `courseIndex` (`course`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `article`(uid,school,college,course,name,brief,hash) values(1,'苏州大学','计算机科学与技术学院','离散数学','这是一个文件','这里是简介','232323232');

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `ticket` varchar(40) NOT NULL,
  `expire` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket_index` (`ticket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `province` varchar(40) NOT NULL,
  `city` varchar(40) NOT NULL,
  `country` varchar(40) NOT NULL,
  `avatar` varchar(100) NOT NULL,
  `openid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
