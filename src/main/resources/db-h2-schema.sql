DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
	USERNAME VARCHAR2(256),
	PASSWORD VARCHAR2(256),
	EMAIL VARCHAR2(256),
	PRIMARY KEY(USERNAME)
);

INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES ('demo', 'demo', 'demo@domain.com');

DROP TABLE IF EXISTS CONTROLS;

CREATE TABLE CONTROLS (
	NAME VARCHAR2(100),
	DESCRIPTION VARCHAR2(2000),
	PRIMARY KEY(NAME)
);

INSERT INTO CONTROLS(NAME, DESCRIPTION) VALUES ('PROJECTION_SEMANAL', 'Comprueba que los usuarios hayan imputado las horas en el projection hasta un determinado día de la semana. Envía un email a los que no lo hayan hecho.');
INSERT INTO CONTROLS(NAME, DESCRIPTION) VALUES ('PROJECTION_MENSUAL', 'Comprueba que los usuarios hayan imputado las horas de todo el mes en curso. Envía un email a los que no lo hayan hecho.');

DROP TABLE IF EXISTS USERS_CONTROLS;

CREATE TABLE USERS_CONTROLS (
	USER_USERNAME VARCHAR2(256),
	CONTROL_NAME VARCHAR2(100),
	PRIMARY KEY(USER_USERNAME, CONTROL_NAME),
	FOREIGN KEY(USER_USERNAME) REFERENCES PUBLIC.USERS(USERNAME),
	FOREIGN KEY(CONTROL_NAME) REFERENCES PUBLIC.CONTROLS(NAME)
);

DROP TABLE IF EXISTS CONTROLS_SCHEDULE;

CREATE TABLE CONTROLS_SCHEDULE (
	ID IDENTITY,
	CONTROL_NAME VARCHAR2(100),
	START TIMESTAMP,
	END TIMESTAMP,
	STATUS VARCHAR2(50),
	CRON VARCHAR2(50),
	PRIMARY KEY(ID),
	FOREIGN KEY(CONTROL_NAME) REFERENCES PUBLIC.CONTROLS(NAME)
);