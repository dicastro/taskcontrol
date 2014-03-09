package com.qopuir.taskcontrol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.qopuir.taskcontrol.entities.ControlVO;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.ControlService;
import com.qopuir.taskcontrol.service.UserService;

@Controller
@RequestMapping("/controls")
public class ControlsController {
	@Autowired
	ControlService controlService;
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<ControlVO> getControls() {
		return controlService.list();
	}
	
	@RequestMapping(value = "/{controlName}/users", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<UserVO> getUserControls(@PathVariable("controlName") ControlName controlName) {
		return userService.listControlUsers(controlName);
	}
}