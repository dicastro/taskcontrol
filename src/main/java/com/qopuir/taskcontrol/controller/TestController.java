package com.qopuir.taskcontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qopuir.taskcontrol.model.Test;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Test getDefaultMovie() {
		return new Test();
	}
}