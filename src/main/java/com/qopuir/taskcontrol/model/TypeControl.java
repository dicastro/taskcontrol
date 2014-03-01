package com.qopuir.taskcontrol.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.STRING)
public enum TypeControl {
	PROJECTION_SEMANAL, PROJECTION_MENSUAL;
}