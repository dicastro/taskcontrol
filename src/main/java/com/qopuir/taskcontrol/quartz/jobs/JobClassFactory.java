package com.qopuir.taskcontrol.quartz.jobs;

import java.util.Map;

import org.quartz.Job;

import com.qopuir.taskcontrol.entities.enums.ControlName;

public class JobClassFactory {
	private Map<ControlName, Class<Job>> map;

	public void setMap(Map<ControlName, Class<Job>> map) {
		this.map = map;
	}
	
	public Class<Job> getJobClass(ControlName controlName) {
		return map.get(controlName);
	}
}