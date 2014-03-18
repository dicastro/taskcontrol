package com.qopuir.taskcontrol.entities.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape= JsonFormat.Shape.STRING)
public enum ParamName implements EnumType {
	MAIL_FROM("MAIL_FROM"), MAIL_SUBJECT("MAIL_SUBJECT"), MAIL_TEMPLATE("MAIL_TEMPLATE"), SIMULATE("SIMULATE"), URL("URL"), TOLERANCE_DAYS("TOLERANCE_DAYS");
	
	private final String literal;
	
	private ParamName(final String literal) {
		this.literal = literal;
	}
	
	@Override
	public String getLiteral() {
		return this.literal;
	}
}