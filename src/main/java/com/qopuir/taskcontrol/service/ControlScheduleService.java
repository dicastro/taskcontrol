package com.qopuir.taskcontrol.service;

import java.util.List;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;

public interface ControlScheduleService {
	/**
     * Create a control schedule
     */
    Long create(ControlScheduleVO controlSchedule);
    
    /**
     * Get list of control schedules
     */
    List<ControlScheduleVO> list();
    
    /**
     * Get a control schedule by id
     */
	ControlScheduleVO findById(Long controlScheduleId);
    
    /**
     * Get control schedules pending
     */
    List<ControlScheduleVO> findReadyToStart();
    
    /**
     * Get control schedules finished
     */
    List<ControlScheduleVO> findReadyToStop();
    
    /**
     * Start a control schedule
     */
    void start(Long controlScheduleId);
    
	/**
	 * Stop a control schedule
	 */
	void stop(Long controlScheduleId);
	
	/**
	 * Execute an action over a control schedule
	 */
	void executeAction(Long controlScheduleId, ControlScheduleAction action);
}