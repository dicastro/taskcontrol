package com.qopuir.taskcontrol.quartz.jobs.rest;

import java.util.List;

public class SaisieErrorsRestClientJobResponse {

	private boolean success;
	private String start;
	private String end;
	private String today;
	private List<SaisieErrorsRestClientJobDataResponse> data;

	public SaisieErrorsRestClientJobResponse() {

	}

	public List<SaisieErrorsRestClientJobDataResponse> getData() {
		return data;
	}

	public String getEnd() {
		return end;
	}

	public String getStart() {
		return start;
	}

	public String getToday() {
		return today;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setData(List<SaisieErrorsRestClientJobDataResponse> data) {
		this.data = data;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setToday(String today) {
		this.today = today;
	}

}
