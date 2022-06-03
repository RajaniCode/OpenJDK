DROP DATABASE IF EXISTS `websystique`;

CREATE DATABASE `websystique`;

USE `websystique`;

DROP TABLE IF EXISTS `EMPLOYEE`;

CREATE TABLE `EMPLOYEE`(
    `id` INT NOT NULL auto_increment, 
    `name` VARCHAR(50) NOT NULL,
    `joining_date` DATE NOT NULL,
    `salary` DOUBLE NOT NULL,
    `ssn` VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

SELECT * FROM `EMPLOYEE`;