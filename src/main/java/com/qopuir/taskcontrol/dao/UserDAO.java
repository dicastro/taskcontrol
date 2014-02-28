package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.User;

public interface UserDAO {
	/**
     * Create a new user.
     */
    @Transactional
    void create(String userName, String password, String email);
    
    /**
     * Get list of users
     */
    @Transactional(readOnly = true)
    List<User> list();
    
    /**
     * Adds a control to a user
     */
    @Transactional
    void addControl(String username, String controlName);

    /**
     * Removes a user's control
     */
	@Transactional
	void removeControl(String username, String controlName);
	
	/**
	 * Get list of control's users
	 */
	@Transactional(readOnly = true)
	List<User> listControlUsers(String controlName);
}