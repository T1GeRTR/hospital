DROP DATABASE IF EXISTS hospital;

CREATE DATABASE `hospital`;

USE `hospital`;

CREATE TABLE `speciality` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (id),
    KEY name (name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `room` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (id),
    KEY name (name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `login` varchar(50) NOT NULL,
    `password` varchar(50) NOT NULL,
    `firstName` varchar(50) NOT NULL,
    `lastName` varchar(50) NOT NULL,
    `patronymic` varchar(50),
    `type` ENUM('ADMIN', 'DOCTOR', 'PATIENT') NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY login (login),
    KEY password (password),
    KEY firstName (firstName),
    KEY lastName (lastName),
    KEY patronymic (patronymic)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `admin` (
    `position` varchar(50) NOT NULL,
    `userId` int(11) NOT NULL,
    PRIMARY KEY (userId),
    KEY position (position),
    FOREIGN KEY (userId) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `doctor` (
    `specialityId` int(11) NOT NULL,
    `roomId` int(11) NOT NULL UNIQUE,
    `userId` int(11) NOT NULL,
    PRIMARY KEY (userId),
    FOREIGN KEY (specialityId) REFERENCES speciality (id) ON DELETE CASCADE,
    FOREIGN KEY (roomId) REFERENCES room (id) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `patient` (
    `email` varchar(50) NOT NULL,
    `address` varchar(50) NOT NULL,
    `phone` varchar(50) NOT NULL,
    `userId` int(11) NOT NULL,
    PRIMARY KEY (userId),
    KEY email (email),
    KEY address (address),
    KEY phone (phone),
    FOREIGN KEY (userId) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `day_schedule` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `date` date NOT NULL,
    `doctorId` int(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY date_doctorId (date, doctorId),
    KEY date (date),
    FOREIGN KEY (doctorId) REFERENCES `doctor` (userId) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `appointment` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `ticket` varchar(50) NOT NULL,
    `timeStart` time NOT NULL,
    `timeEnd` time NOT NULL,
    `patientId` int(11) DEFAULT NULL,
    `appointmentState` ENUM('FREE', 'TICKET', 'BUSY') DEFAULT 'FREE' NOT NULL,
    `day_scheduleId` int(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY time_scheduleId (timeStart, day_scheduleId),
    KEY ticket (ticket),
    KEY timeStart (timeStart),
    KEY timeEnd (timeEnd),
    KEY appointmentState (appointmentState),
    FOREIGN KEY (day_scheduleId) REFERENCES `day_schedule` (id) ON DELETE CASCADE,
    FOREIGN KEY (patientId) REFERENCES `patient` (userId) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `session` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `sessionId` varchar(50) NOT NULL,
    `userId` int(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY sessionId (sessionId),
    FOREIGN KEY (userId) REFERENCES `user` (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `comission` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `ticket` varchar(50) NOT NULL,
    `timeStart` time NOT NULL,
    `timeEnd` time NOT NULL,
    `patientId` int(11),
    `date` date NOT NULL,
    `roomId` int(11) NOT NULL,
    PRIMARY KEY (id),
	UNIQUE KEY ticket (ticket),
    KEY timeStart (timeStart),
    KEY timeEnd (timeEnd),
    KEY date (date),
    FOREIGN KEY (roomId) REFERENCES `room` (id) ON DELETE CASCADE,
    FOREIGN KEY (patientId) REFERENCES `patient` (userId) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `comission_doctor` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `doctorId` int(11) NOT NULL,
    `comissionId` int(11) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (doctorId) REFERENCES `doctor` (userId) ON DELETE CASCADE,
    FOREIGN KEY (comissionId) REFERENCES `comission` (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `speciality` (name) VALUES ('dentist'), ('surgeon'), ('urologist'), ('neurologist'), ('psychiatrist'), ('otolaryngologist'), ('obstetrician'), ('dermatologist'), ('cardiologist'), ('endocrinologist'), ('oncologist'), ('rheumatologist'), ('nephrologist'), ('pediatrician'), ('podiatrist'), ('ophthalmologist'), ('radiologist'), ('gynecologist'), ('geneticist'), ('physiologist'), ('allergist'), ('neurosurgeon'), ('hematologist'), ('microbiologist'), ('epidemiologist'), ('anaesthesiologist'), ('gastroenterologist');
INSERT INTO `room` (name) VALUES ('1'), ('2'), ('3'), ('4'), ('5'), ('6'), ('7'), ('8'), ('9'), ('10'), ('11'), ('12'), ('13'), ('14'), ('15'), ('16'), ('17'), ('18'), ('19'), ('20'), ('21'), ('22'), ('23'), ('24'), ('25'), ('26'), ('27'), ('28'), ('29'), ('30'), ('31'), ('32'), ('33'), ('34'), ('35'), ('36'), ('37'), ('38'), ('39'), ('40'), ('41'), ('42'), ('43'), ('44'), ('45'), ('46'), ('47'), ('48'), ('49'), ('50');
INSERT INTO `user`(firstName, lastName, patronymic, login, password, type) VALUES ('Иван', 'Иванов', 'Иванович', 'admin',  'passAdmin', 'ADMIN');
INSERT INTO `admin` (position, userId) VALUES ('Главный администратор', 1);