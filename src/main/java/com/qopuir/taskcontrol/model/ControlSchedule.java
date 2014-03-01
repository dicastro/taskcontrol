package com.qopuir.taskcontrol.model;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ControlSchedule {
	private Long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private String cron;
	private TypeControl type;
	private ControlScheduleStatus status;

	public ControlSchedule() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public TypeControl getType() {
		return type;
	}

	public void setType(TypeControl type) {
		this.type = type;
	}

	public ControlScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ControlScheduleStatus status) {
		this.status = status;
	}
}