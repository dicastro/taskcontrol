package com.qopuir.taskcontrol.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("start", start.toString("dd/MM/yyyy hh:mm:ss")).append("end", end.toString("dd/MM/yyyy hh:mm:ss")).append("cron", cron).append("controlName", controlName.getLiteral()).append("status", status.getLiteral()).build();
	}
}