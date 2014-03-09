package com.qopuir.taskcontrol.dao;

import com.qopuir.taskcontrol.entities.ControlUserVO;

public interface ControlUserDAO {
    /**
     * Create a control for a user
     */
    void create(ControlUserVO controlUser);
    
    /**
     * Remove a control from a user
     */
    void remove(ControlUserVO controlUser);
}