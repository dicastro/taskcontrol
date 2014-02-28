package com.qopuir.taskcontrol.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.ControlSchedule;

public interface ControlScheduleService {
	/**
     * Create a control schedule
     */
    @Transactional
    void create(ControlSchedule controlSchedule);
    
    /**
     * Get list of control schedules
     */
    @Transactional(readOnly = true)
    List<ControlSchedule> list();
    
    /**
     * Get a control schedule by id
     */
    @Transactional(readOnly = true)
	ControlSchedule findOne(Long controlId);
    
    /**
     * Pause a control schedule
     */
    @Transactional
    void pause(Long controlId);

    /**
     * Resume a control schedule
     */
	@Transactional
	void resume(Long controlId);
}