package com.qopuir.taskcontrol.dao;

import java.util.List;

import com.qopuir.taskcontrol.entities.ControlVO;

public interface ControlDAO {
    /**
     * Get list of controls
     */
    List<ControlVO> list();
    
    /**
     * Get list of controls of user
     */
    List<ControlVO> listUserControls(String username);
}