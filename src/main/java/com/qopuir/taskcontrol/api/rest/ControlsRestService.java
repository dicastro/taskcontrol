package com.qopuir.taskcontrol.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.ControlService;
import com.qopuir.taskcontrol.service.UserService;

@Path("/controls")
public class ControlsRestService {
	@Autowired
	ControlService controlService;
	@Autowired
	UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getControls() {
		return Response.status(Status.OK).entity(controlService.list()).build();
	}

	@GET
	@Path("/{controlName}/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserControls(@PathParam("controlName") ControlName controlName) {
		return Response.status(Status.OK).entity(userService.listControlUsers(controlName)).build();
	}
}