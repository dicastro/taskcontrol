package com.qopuir.taskcontrol.service;

import java.util.List;

import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

public interface UserService {
	/**
     * Create a new user.
     */
    void create(UserVO user);
    
    /**
     * Get list of users
     */
    List<UserVO> list();
    
    /**
     * Adds a control to a user
     */
    void addControl(String username, ControlName controlName);

    /**
     * Removes a user's control
     */
	void removeControl(String username, ControlName controlName);
	
	/**
	 * Get list of control's users
	 */
	List<UserVO> listControlUsers(ControlName controlName);
}