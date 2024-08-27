CREATE USER 'account'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'account'@'localhost' WITH GRANT OPTION;

CREATE DATABASE IF NOT EXISTS job_seek_app;
USE job_seek_app;

CREATE TABLE `role` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(255) NOT NULL
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

INSERT INTO `job_seek_app`.`role` (`role_name`) VALUES ('ROLE_EMPLOYER');
INSERT INTO `job_seek_app`.`role` (`role_name`) VALUES ('ROLE_SEEKER');

CREATE TABLE cv (
    id INT PRIMARY KEY AUTO_INCREMENT,
    file_name VARCHAR(255) NOT NULL
);

CREATE TABLE `user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    `description` VARCHAR(255),
    full_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255),
    `status` INT NOT NULL,
    role_id INT NOT NULL,
    cv_id INT,
    FOREIGN KEY (role_id)
        REFERENCES role (id),
    FOREIGN KEY (cv_id)
        REFERENCES cv (id)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE company (
    id INT PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(255),
    `description` VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    name_company VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    `status` INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES `user` (id)
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    number_choose INT NOT NULL DEFAULT 0
);

INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Back-end Developer');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Front-end Developer');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Full-stack Developer');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Business Analyst');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Tester');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Solution Architect');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Marketing');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Sales');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Education');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Hospitality and Restaurant');
INSERT INTO `job_seek_app`.`CATEGORY` (`name`) VALUES ('Internship');

CREATE TABLE recruitment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(255),
    `description` VARCHAR(255),
    experience VARCHAR(255),
    quantity INT,
    salary VARCHAR(255),
    `status` INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    `type` VARCHAR(255),
    category_id INT,
    company_id INT NOT NULL,
    deadline VARCHAR(255),
    FOREIGN KEY (company_id)
        REFERENCES company (id),
    FOREIGN KEY (category_id)
        REFERENCES category (id)
);

CREATE TABLE follow_company (
    id INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (company_id)
        REFERENCES company (id),
    FOREIGN KEY (user_id)
        REFERENCES `user` (id)
);

CREATE TABLE applypost (
    id INT PRIMARY KEY AUTO_INCREMENT,
    recruitment_id INT NOT NULL,
    user_id INT NOT NULL,
    cv VARCHAR(255) NOT NULL,
    `status` INT NOT NULL,
    `text` VARCHAR(255),
    FOREIGN KEY (recruitment_id)
        REFERENCES recruitment (id),
    FOREIGN KEY (user_id)
        REFERENCES `user` (id)
);

CREATE TABLE save_job (
    id INT PRIMARY KEY AUTO_INCREMENT,
    recruitment_id INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (recruitment_id)
        REFERENCES recruitment (id),
    FOREIGN KEY (user_id)
        REFERENCES `user` (id)
);