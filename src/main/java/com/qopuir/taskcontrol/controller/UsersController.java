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

import com.qopuir.taskcontrol.model.Control;
import com.qopuir.taskcontrol.model.User;
import com.qopuir.taskcontrol.services.ControlService;
import com.qopuir.taskcontrol.services.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
	@Autowired
	UserService userService;
	@Autowired
	ControlService controlService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<User> getUsers() {
		return userService.list();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void createUser(@RequestBody User user) {
		userService.create(user.getUsername(), user.getPassword());
	}
	
	@RequestMapping(value = "/{userId}/controls/{controlId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public void addUserControl(@PathVariable("userId") Long userId, @PathVariable("controlId") Long controlId) {
		userService.addUserControl(userId, controlId);
	}
	
	@RequestMapping(value = "/{userId}/controls/{controlId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ResponseBody
	public void removeUserControl(@PathVariable("userId") Long userId, @PathVariable("controlId") Long controlId) {
		userService.removeUserControl(userId, controlId);
	}
	
	@RequestMapping(value = "/{userId}/controls", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Control> getUserControls(@PathVariable("userId") Long userId) {
		return controlService.listUserControls(userId);
	}
}