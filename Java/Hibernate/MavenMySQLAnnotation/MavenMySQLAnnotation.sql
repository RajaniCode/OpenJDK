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

DROP DATABASE IF EXISTS `hibernatedb`;
CREATE DATABASE `hibernatedb`;
USE `hibernatedb`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(50) NOT NULL, 
    `login_date` DATE NOT NULL,
    `login_time` TIME NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,    
    CONSTRAINT `pk_id` PRIMARY KEY(`id`),
    CONSTRAINT `idx_username` UNIQUE(`username`)    
);

SELECT * FROM users;

/*

INSERT INTO users(username, login_date, login_time, created_at, updated_at)
VALUES('Foo', '2016-11-06', '10:49:35', '2016-11-06 10:49:35.0', '2016-11-06 10:49:35.0');

SELECT * FROM users;

*/