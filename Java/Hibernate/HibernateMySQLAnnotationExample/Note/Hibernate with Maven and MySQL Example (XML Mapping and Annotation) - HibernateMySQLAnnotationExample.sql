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

DROP DATABASE IF EXISTS `tutorials`;
CREATE DATABASE `tutorials`;
USE `tutorials`;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
    `STUDENT_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    `STUDENT_NAME` VARCHAR(10) NOT NULL,
    `STUDENT_AGE` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`STUDENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM `STUDENT`;