package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.Control;

public interface ControlDAO {
    /**
     * Get list of controls
     */
    @Transactional(readOnly = true)
    List<Control> list();
    
    /**
     * Get list of controls of user
     */
    @Transactional(readOnly = true)
    List<Control> listUserControls(String username);
}