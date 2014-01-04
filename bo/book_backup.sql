/* 
SQLyog v3.71
Host - 10.4.3.233 : Database - defa4485eb44a411387d342eb8c124d65
**************************************************************
Server version 4.1.7-nt
*/

create database if not exists `defa4485eb44a411387d342eb8c124d65`;

use `defa4485eb44a411387d342eb8c124d65`;

/*
Table structure for book
*/

drop table if exists `book`;
CREATE TABLE `book` (
  `id` int(6) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL default '',
  `author` varchar(100) NOT NULL default '',
  `publisher` varchar(50) NOT NULL default '',
  `publish_year` year(4) NOT NULL default '0000',
  `total` int(3) NOT NULL default '0',
  `leave_number` int(3) NOT NULL default '0',
  `other` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
Table data for defa4485eb44a411387d342eb8c124d65.book
*/

INSERT INTO `book` VALUES 
(1,'PHPjiaochen','zhangyi','dianzichubanshe','2000',5,4,''),
(3,'mysqlzhinan','wangla','renmingchubanshe','2001',4,4,''),
(4,'phpshujukushili','guoxiang','jixiedianzichubanshe','2002',5,4,'');

/*
Table structure for lend
*/

drop table if exists `lend`;
CREATE TABLE `lend` (
  `id` int(6) NOT NULL auto_increment,
  `book_id` int(6) NOT NULL default '0',
  `book_title` varchar(100) NOT NULL default '',
  `lend_time` date NOT NULL default '0000-00-00',
  `renew_time` date default NULL,
  `user_id` int(3) NOT NULL default '0',
  PRIMARY KEY  (`id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
Table data for defa4485eb44a411387d342eb8c124d65.lend
*/

INSERT INTO `lend` VALUES 
(3,1,'PHPjiaoc','2005-01-16',NULL,1),
(4,4,'phpshujukushili','2005-01-16','2005-01-17',1);

/*
Table structure for lend_log
*/

drop table if exists `lend_log`;
CREATE TABLE `lend_log` (
  `id` int(8) NOT NULL auto_increment,
  `book_id` int(6) NOT NULL default '0',
  `user_id` int(3) NOT NULL default '0',
  `lend_time` date NOT NULL default '0000-00-00',
  `return_time` date default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
Table data for defa4485eb44a411387d342eb8c124d65.lend_log
*/

INSERT INTO `lend_log` VALUES 
(2,1,1,'2005-01-15','2005-01-16'),
(3,1,1,'2005-01-16',NULL),
(4,4,1,'2005-01-16',NULL);

/*
Table structure for user
*/

drop table if exists `user`;
CREATE TABLE `user` (
  `id` int(3) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL default '',
  `password` varchar(50) NOT NULL default '',
  `address` varchar(50) default NULL,
  `tel` varchar(20) default NULL,
  `email` varchar(50) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*
Table data for defa4485eb44a411387d342eb8c124d65.user
*/

INSERT INTO `user` VALUES 
(1,'lilan','e10adc3949ba59abbe56e057f20f883e','','','l@t.m');
