package com.qopuir.taskcontrol.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.User;

public interface UserService {
	/**
     * Create a new user.
     */
    @Transactional
    void create(String userName, String password);
    
    /**
     * Get list of users
     */
    @Transactional(readOnly = true)
    List<User> list();
    
    /**
     * Adds a control to a user
     */
    @Transactional
    void addUserControl(Long userId, Long controlId);

    /**
     * Removes a user's control
     */
	@Transactional
	void removeUserControl(Long userId, Long controlId);
	
	/**
	 * Get list of control's users
	 */
	@Transactional(readOnly = true)
	List<User> listControlUsers(Long controlId);
}