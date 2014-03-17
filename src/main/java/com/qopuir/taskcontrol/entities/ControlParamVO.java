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
public class ControlParamVO {
	private ControlName controlName;
	private ParamName paramName;
	private Boolean required;
	private String defaultValue;
	private String description;

	public ControlParamVO() {
	}

	public ControlName getControlName() {
		return controlName;
	}

	public ControlParamVO setControlName(ControlName controlName) {
		this.controlName = controlName;
		return this;
	}

	public ParamName getParamName() {
		return paramName;
	}

	public ControlParamVO setParamName(ParamName paramName) {
		this.paramName = paramName;
		return this;
	}

	public Boolean getRequired() {
		return required;
	}

	public ControlParamVO setRequired(Boolean required) {
		this.required = required;
		return this;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public ControlParamVO setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ControlParamVO setDescription(String description) {
		this.description = description;
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

		ControlParamVO other = (ControlParamVO) obj;
		
		return new EqualsBuilder().append(controlName, other.controlName).append(paramName, other.paramName).build();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 1).append(controlName).append(paramName).build();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("paramName", paramName)
				.append("controlName", controlName)
				.append("required", required)
				.append("defaultValue", defaultValue)
				.append("description", description).build();
	}
}