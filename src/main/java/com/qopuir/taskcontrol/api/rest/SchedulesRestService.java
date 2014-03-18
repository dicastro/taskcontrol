package com.qopuir.taskcontrol.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.service.ControlScheduleService;

@Path("/schedules")
public class SchedulesRestService {
	@Autowired
	ControlScheduleService controlScheduleService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchedules() {
		return Response.status(Status.OK).entity(controlScheduleService.list()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createControlSchedule(ControlScheduleVO controlSchedule) {
		controlScheduleService.create(controlSchedule);
		
		return Response.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{controlSecheduleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateControlSchedule(@PathParam("controlSecheduleId") Long controlScheduleId, @QueryParam("action") ControlScheduleAction action) {
		controlScheduleService.executeAction(controlScheduleId, action);
		
		return Response.status(Status.OK).build();
	}
}