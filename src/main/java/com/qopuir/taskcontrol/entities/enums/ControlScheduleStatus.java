package com.qopuir.taskcontrol.entities.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.STRING)
public enum ControlScheduleStatus implements EnumType {
	PENDING("PENDING"), RUNNING("RUNNING"), FINISHED("FINISHED"), PAUSED("PAUSED");
	
	private final String literal;
	
	private ControlScheduleStatus(String literal) {
		this.literal = literal;
	}
	
	@Override
	public String getLiteral() {
		return this.literal;
	}
}