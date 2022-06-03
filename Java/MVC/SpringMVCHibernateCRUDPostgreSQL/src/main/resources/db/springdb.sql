DROP DATABASE IF EXISTS springdb;

CREATE DATABASE springdb;

-- \connect springdb;

DROP TABLE IF EXISTS Movie;
CREATE TABLE Movie
(
    id SERIAL NOT NULL,
    title varchar(50) NOT NULL,
    release_date date NOT NULL,
    price double precision NOT NULL,
    genre varchar(50) NOT NULL,
    rating varchar(50) NOT NULL,
    PRIMARY KEY(id)
);

SELECT * FROM Movie;

INSERT INTO Movie(title, release_date, price, genre, rating)
VALUES('The Post', '2017-12-14', 2.17, 'Historical Political Thriller', 'G');

INSERT INTO Movie(title, release_date, price, genre, rating)
VALUES('Swordfish', '2001-06-08', 2.01, 'Action Crime Thriller', 'PG');

INSERT INTO Movie(title, release_date, price, genre, rating)
VALUES('The Net', '1995-07-28', 1.95, 'Cyber Mystery Thriller', 'PG');

SELECT * FROM Movie;

-- \connect springdb;

SELECT * FROM Movie;