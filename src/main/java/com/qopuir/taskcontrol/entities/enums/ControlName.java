package com.qopuir.taskcontrol.entities.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.STRING)
public enum ControlName implements EnumType {
	PROJECTION_SEMANAL("PROJECTION_SEMANAL"), PROJECTION_MENSUAL("PROJECTION_MENSUAL");
	
	private final String literal;
	
	private ControlName(final String literal) {
		this.literal = literal;
	}
	
	@Override
	public String getLiteral() {
		return this.literal;
	}
}