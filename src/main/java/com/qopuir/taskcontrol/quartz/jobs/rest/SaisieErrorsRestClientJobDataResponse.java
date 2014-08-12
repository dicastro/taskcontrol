package com.qopuir.taskcontrol.quartz.jobs.rest;

import java.util.List;

public class SaisieErrorsRestClientJobDataResponse {

	private String username;
	private List<SaisieErrorsRestClientJobDataErrorsResponse> errors;

	public SaisieErrorsRestClientJobDataResponse() {
	}

	public List<SaisieErrorsRestClientJobDataErrorsResponse> getErrors() {
		return errors;
	}

	public String getUsername() {
		return username;
	}

	public void setErrors(List<SaisieErrorsRestClientJobDataErrorsResponse> errors) {
		this.errors = errors;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
