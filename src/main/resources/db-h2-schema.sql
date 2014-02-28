DROP TABLE IF EXISTS users;

CREATE TABLE users (
	USERNAME VARCHAR(256),
	PASSWORD VARCHAR(256),
	EMAIL VARCHAR(256),
	PRIMARY KEY(USERNAME)
);

INSERT INTO users(USERNAME, PASSWORD, EMAIL) VALUES ('demo', 'demo', 'demo@domain.com');

DROP TABLE IF EXISTS controls;

CREATE TABLE controls (
	NAME VARCHAR(100),
	DESCRIPTION VARCHAR(2000),
	PRIMARY KEY(NAME)
);

INSERT INTO controls(NAME, DESCRIPTION) VALUES ('PROJECTION_SEMANAL', 'Comprueba que los usuarios hayan imputado las horas en el projection hasta un determinado día de la semana. Envía un email a los que no lo hayan hecho.');
INSERT INTO controls(NAME, DESCRIPTION) VALUES ('PROJECTION_MENSUAL', 'Comprueba que los usuarios hayan imputado las horas de todo el mes en curso. Envía un email a los que no lo hayan hecho.');

DROP TABLE IF EXISTS users_controls;

CREATE TABLE users_controls (
	USER_USERNAME VARCHAR(256),
	CONTROL_NAME VARCHAR(100),
	PRIMARY KEY(USER_USERNAME, CONTROL_NAME),
	FOREIGN KEY(USER_USERNAME) REFERENCES PUBLIC.USERS(USERNAME),
	FOREIGN KEY(CONTROL_NAME) REFERENCES PUBLIC.CONTROLS(NAME)
);