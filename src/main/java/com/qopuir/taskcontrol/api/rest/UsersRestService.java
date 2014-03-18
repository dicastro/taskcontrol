package com.qopuir.taskcontrol.api.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.ControlService;
import com.qopuir.taskcontrol.service.UserService;

@Path("/users")
public class UsersRestService {
	@Autowired
	UserService userService;
	@Autowired
	ControlService controlService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.status(Status.OK).entity(userService.list()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(UserVO user) {
		userService.create(user);
		
		return Response.status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/{username}/controls/{controlName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUserControl(@PathParam("username") String username, @PathParam("controlName") ControlName controlName) {
		userService.addControl(username, controlName);
		
		return Response.status(Status.CREATED).build();
	}
	
	@DELETE
	@Path("/{username}/controls/{controlName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeUserControl(@PathParam("userName") String username, @PathParam("controlName") ControlName controlName) {
		userService.removeControl(username, controlName);
		
		return Response.status(Status.NO_CONTENT).build();
	}
	
	@GET
	@Path("/{username}/controls")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserControls(@PathParam("username") String username) {
		return Response.status(Status.OK).entity(controlService.listUserControls(username)).build();
	}
}