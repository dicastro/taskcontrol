package com.qopuir.taskcontrol.service;

import java.util.List;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;

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
	ControlScheduleVO findById(Long controlId);
    
    /**
     * Get control schedules pending
     */
    List<ControlScheduleVO> findReadyToRun();
    
    /**
     * Get control schedules finished
     */
    List<ControlScheduleVO> findReadyToFinish();
    
    /**
     * Start a control schedule
     */
    void start(Long controlId);
    
    /**
     * Start a list of control schedules
     */
    void start(List<ControlScheduleVO> controlSchedules);
    
    /**
     * Pause a control schedule
     */
    void pause(Long controlId);

    /**
     * Resume a control schedule
     */
	void resume(Long controlId);
	
	/**
	 * Finish a control schedule
	 */
	void finish(Long controlId);
	
	/**
	 * Finish a list of control schedules
	 */
	void finish(List<ControlScheduleVO> controlSchedules);
}