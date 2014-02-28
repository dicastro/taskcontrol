DROP TABLE IF EXISTS users;

CREATE TABLE users (
	ID BIGINT AUTO_INCREMENT,
	USERNAME VARCHAR(256),
	PASSWORD VARCHAR(256),
	EMAIL VARCHAR(256),
	PRIMARY KEY(ID)
);

INSERT INTO users(USERNAME, PASSWORD, EMAIL) VALUES ('demo', 'demo', 'demo@domain.com');

DROP TABLE IF EXISTS controls;

CREATE TABLE controls (
	ID BIGINT AUTO_INCREMENT,
	NAME VARCHAR(100),
	DESCRIPTION VARCHAR(2000),
	PRIMARY KEY(ID)
);

INSERT INTO controls(NAME, DESCRIPTION) VALUES ('PROJECTION_SEMANAL', 'Comprueba que los usuarios hayan imputado las horas en el projection hasta un determinado día de la semana. Envía un email a los que no lo hayan hecho.');
INSERT INTO controls(NAME, DESCRIPTION) VALUES ('PROJECTION_MENSUAL', 'Comprueba que los usuarios hayan imputado las horas de todo el mes en curso. Envía un email a los que no lo hayan hecho.');

DROP TABLE IF EXISTS users_controls;

CREATE TABLE users_controls (
	USER_ID BIGINT,
	CONTROL_ID BIGINT,
	PRIMARY KEY(USER_ID, CONTROL_ID)
);