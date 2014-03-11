package com.qopuir.taskcontrol.entities.enums;

public enum ControlScheduleAction implements EnumType {
	START("START"), STOP("STOP"), PAUSE("PAUSE");
	
	private final String literal;
	
	private ControlScheduleAction(String literal) {
		this.literal = literal;
	}
	
	@Override
	public String getLiteral() {
		return this.literal;
	}
}