package com.qopuir.taskcontrol.entities;

import com.qopuir.taskcontrol.entities.enums.ControlName;

public class ControlUserVO {
	private String userUsername;
	private ControlName controlName;

	public String getUserUsername() {
		return userUsername;
	}

	public ControlUserVO setUserUsername(String userUsername) {
		this.userUsername = userUsername;
		return this;
	}

	public ControlName getControlName() {
		return controlName;
	}

	public ControlUserVO setControlName(ControlName controlName) {
		this.controlName = controlName;
		return this;
	}
}