package com.qopuir.taskcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.service.ControlScheduleService;

@Controller
@RequestMapping("/schedules")
public class SchedulesController {
	@Autowired
	ControlScheduleService controlScheduleService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ControlScheduleVO> getSchedules() {
		return controlScheduleService.list();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createControlSchedule(@RequestBody ControlScheduleVO controlSchedule) {
		controlScheduleService.create(controlSchedule);
	}
	
	@RequestMapping(value = "/{scheduleId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void updateControlSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ControlScheduleVO controlSchedule) {
		switch (controlSchedule.getStatus()) {
		case PAUSED:
			controlScheduleService.pause(scheduleId);
			break;
		case RUNNING:
			controlScheduleService.resume(scheduleId);
			break;
		case FINISHED:
			controlScheduleService.finish(scheduleId);
			break;
		default:
			break;
		}
	}
}