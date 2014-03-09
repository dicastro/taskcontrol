package com.qopuir.taskcontrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlUserDAO;
import com.qopuir.taskcontrol.dao.UserDAO;
import com.qopuir.taskcontrol.entities.ControlUserVO;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    UserDAO userDAO;
	@Autowired
    ControlUserDAO controlUserDAO;
	
	@Override
	@Transactional
	public void create(UserVO userVO) {
		userDAO.create(userVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserVO> list() {
		return userDAO.list();
	}
	
	@Override
	@Transactional
	public void addControl(String username, ControlName controlName) {
		controlUserDAO.create(new ControlUserVO().setUserUsername(username).setControlName(controlName));
	}
	
	@Override
	@Transactional
	public void removeControl(String username, ControlName controlName) {
        controlUserDAO.remove(new ControlUserVO().setUserUsername(username).setControlName(controlName));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserVO> listControlUsers(ControlName controlName) {
		return userDAO.listControlUsers(controlName);
	}
}