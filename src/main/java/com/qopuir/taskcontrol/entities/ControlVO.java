package com.qopuir.taskcontrol.entities;

import com.qopuir.taskcontrol.entities.enums.ControlName;

public class ControlVO {
	private ControlName name;
	private String description;

	public ControlName getName() {
		return name;
	}

	public ControlVO setName(ControlName name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ControlVO setDescription(String description) {
		this.description = description;
		return this;
	}
}