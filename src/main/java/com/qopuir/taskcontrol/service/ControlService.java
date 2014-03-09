package com.qopuir.taskcontrol.service;

import java.util.List;

import com.qopuir.taskcontrol.entities.ControlVO;

public interface ControlService {
    /**
     * Get list of controls
     */
    List<ControlVO> list();
    
    /**
     * Get list of controls of user
     */
    List<ControlVO> listUserControls(String username);
}