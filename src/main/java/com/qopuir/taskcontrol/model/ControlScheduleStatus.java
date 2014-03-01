package com.qopuir.taskcontrol.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.STRING)
public enum ControlScheduleStatus {
	PENDING, RUNNING, FINISHED, PAUSED;
}