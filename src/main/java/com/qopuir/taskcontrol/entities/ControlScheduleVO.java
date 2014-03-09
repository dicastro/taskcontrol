package com.qopuir.taskcontrol.entities;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.entities.enums.ControlName;

@JsonInclude(Include.NON_NULL)
public class ControlScheduleVO {
	private Long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private String cron;
	private ControlName controlName;
	private ControlScheduleStatus status;

	public ControlScheduleVO() {
	}
	
	public Long getId() {
		return id;
	}

	public ControlScheduleVO setId(Long id) {
		this.id = id;
		return this;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public ControlScheduleVO setStart(LocalDateTime start) {
		this.start = start;
		return this;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public ControlScheduleVO setEnd(LocalDateTime end) {
		this.end = end;
		return this;
	}

	public String getCron() {
		return cron;
	}

	public ControlScheduleVO setCron(String cron) {
		this.cron = cron;
		return this;
	}

	public ControlName getControlName() {
		return controlName;
	}

	public ControlScheduleVO setControlName(ControlName type) {
		this.controlName = type;
		return this;
	}

	public ControlScheduleStatus getStatus() {
		return status;
	}

	public ControlScheduleVO setStatus(ControlScheduleStatus status) {
		this.status = status;
		return this;
	}
}