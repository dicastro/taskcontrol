package com.qopuir.taskcontrol.quartz.jobs.rest;

public class SaisieErrorsRestClientJobDataErrorsResponse {

	private String date;
	private float saisie;

	public SaisieErrorsRestClientJobDataErrorsResponse() {
	}

	public String getDate() {
		return date;
	}

	public float getSaisie() {
		return saisie;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSaisie(float saisie) {
		this.saisie = saisie;
	}

}
