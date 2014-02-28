package com.qopuir.taskcontrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.UserDAO;
import com.qopuir.taskcontrol.model.User;
import com.qopuir.taskcontrol.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    UserDAO userDAO;
	
	@Override
	@Transactional
	public void create(User user) {
		userDAO.create(user.getUsername(), user.getPassword(), user.getEmail());
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> list() {
		return userDAO.list();
	}
	
	@Override
	@Transactional
	public void addControl(String username, String controlName) {
        userDAO.addControl(username, controlName);
	}
	
	@Override
	@Transactional
	public void removeControl(String username, String controlName) {
        userDAO.removeControl(username, controlName);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> listControlUsers(String controlName) {
		return userDAO.listControlUsers(controlName);
	}
}