package com.qopuir.taskcontrol.service;

import java.util.List;

import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

public interface UserService {
	/**
	 * Adds a control to a user
	 */
	void addControl(String username, ControlName controlName);

	/**
	 * Create a new user.
	 */
	void create(UserVO user);

	/**
	 * 
	 * @param userName
	 * @return a user given its username
	 */
	UserVO getUserByUsername(String userName);

	/**
	 * Get list of users
	 */
	List<UserVO> list();

	/**
	 * Get list of control's users
	 */
	List<UserVO> listControlUsers(ControlName controlName);

	/**
	 * Removes a user's control
	 */
	void removeControl(String username, ControlName controlName);
}