#jdbc:mysql://localhost:3306
#root
#My$ql@Server#5.7

/*
Root Account Password: My$ql@Server#5.7

MySQL User Details
Username: aspire
Host: localhost
Role: DB Admin
Authentication: MySQL
Password: My$ql@Server#5.7.14
*/

DROP DATABASE IF EXISTS `websystique`;
CREATE DATABASE `websystique`;
USE `websystique`;

DROP TABLE IF EXISTS `STUDENT`;
CREATE TABLE `STUDENT`(
   `id` INT NOT NULL auto_increment PRIMARY KEY,
   `first_name` VARCHAR(30) NOT NULL,
   `last_name`  VARCHAR(30) NOT NULL,
   `section`    VARCHAR(30) NOT NULL 
);

SELECT * FROM `STUDENT`;