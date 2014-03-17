package com.qopuir.taskcontrol.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ParamName;

@JsonInclude(Include.NON_NULL)
public class ControlScheduleParamVO {
	private Long controlScheduleId;
	private ControlName controlName;
	private ParamName paramName;
	private String value;

	public ControlScheduleParamVO() {
	}

	public Long getControlScheduleId() {
		return controlScheduleId;
	}

	public ControlScheduleParamVO setControlScheduleId(Long controlScheduleId) {
		this.controlScheduleId = controlScheduleId;
		return this;
	}

	public ControlName getControlName() {
		return controlName;
	}

	public ControlScheduleParamVO setControlName(ControlName controlName) {
		this.controlName = controlName;
		return this;
	}

	public ParamName getParamName() {
		return paramName;
	}

	public ControlScheduleParamVO setParamName(ParamName paramName) {
		this.paramName = paramName;
		return this;
	}

	public String getValue() {
		return value;
	}

	public ControlScheduleParamVO setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		ControlScheduleParamVO other = (ControlScheduleParamVO) obj;
		
		return new EqualsBuilder().append(controlScheduleId, other.controlScheduleId).append(paramName, other.paramName).build();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 1).append(controlScheduleId).append(paramName).build();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("controlScheduleId", controlScheduleId)
				.append("paramName", paramName)
				.append("controlName", controlName)
				.append("value", value).build();
	}
}