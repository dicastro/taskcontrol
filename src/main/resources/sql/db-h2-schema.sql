DROP SCHEMA IF EXISTS TASKCONTROL;
CREATE SCHEMA TASKCONTROL;

DROP TABLE IF EXISTS TASKCONTROL.USER_CONTROLS;
DROP TABLE IF EXISTS TASKCONTROL.USERS;
DROP TABLE IF EXISTS TASKCONTROL.CONTROL_SCHEDULE_PARAMS;
DROP TABLE IF EXISTS TASKCONTROL.CONTROL_SCHEDULES;
DROP TABLE IF EXISTS TASKCONTROL.CONTROL_PARAMS;
DROP TABLE IF EXISTS TASKCONTROL.CONTROLS;

CREATE TABLE TASKCONTROL.USERS (
	USERNAME VARCHAR(50),
	PASSWORD VARCHAR(50),
	EMAIL VARCHAR(255),
	PRIMARY KEY(USERNAME)
);

CREATE TABLE TASKCONTROL.CONTROLS (
	NAME VARCHAR(50),
	DESCRIPTION VARCHAR(2000),
	PRIMARY KEY(NAME)
);

CREATE TABLE TASKCONTROL.CONTROL_PARAMS (
	CONTROL_NAME VARCHAR(50),
	PARAM_NAME VARCHAR(50),
	REQUIRED BIT(1) DEFAULT 0,
	DEFAULT_VALUE VARCHAR(255),
	DESCRIPTION VARCHAR(2000),
	PRIMARY KEY(CONTROL_NAME, PARAM_NAME),
	FOREIGN KEY(CONTROL_NAME) REFERENCES TASKCONTROL.CONTROLS(NAME)
) ENGINE=InnoDB;


CREATE TABLE TASKCONTROL.USER_CONTROLS (
	USER_USERNAME VARCHAR(50),
	CONTROL_NAME VARCHAR(50),
	PRIMARY KEY(USER_USERNAME, CONTROL_NAME),
	FOREIGN KEY(USER_USERNAME) REFERENCES TASKCONTROL.USERS(USERNAME),
	FOREIGN KEY(CONTROL_NAME) REFERENCES TASKCONTROL.CONTROLS(NAME)
);

CREATE TABLE TASKCONTROL.CONTROL_SCHEDULES (
	ID IDENTITY,
	CONTROL_NAME VARCHAR(50),
	DESCRIPTION VARCHAR(2000),
	START_DATE TIMESTAMP,
	END_DATE TIMESTAMP,
	SCHEDULE_STATUS VARCHAR(50),
	CRON VARCHAR(50),
	PRIMARY KEY(ID),
	FOREIGN KEY(CONTROL_NAME) REFERENCES TASKCONTROL.CONTROLS(NAME)
);

CREATE TABLE TASKCONTROL.CONTROL_SCHEDULE_PARAMS (
	CONTROL_SCHEDULE_ID BIGINT,
	CONTROL_NAME VARCHAR(50),
	PARAM_NAME VARCHAR(50),
	PARAM_VALUE VARCHAR(255),
	PRIMARY KEY(CONTROL_SCHEDULE_ID, PARAM_NAME),
	FOREIGN KEY(CONTROL_NAME, PARAM_NAME) REFERENCES TASKCONTROL.CONTROL_PARAMS(CONTROL_NAME, PARAM_NAME)
) ENGINE=InnoDB;