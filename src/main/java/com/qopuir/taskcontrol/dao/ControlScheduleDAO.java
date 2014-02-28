package com.qopuir.taskcontrol.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.ControlSchedule;

public interface ControlScheduleDAO {
	/**
     * Create a control schedule
     */
    @Transactional
    Long create(Timestamp start, Timestamp end, String cron, String typeControl);
    
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
     * Get control schedules ready to be run
     */
    @Transactional(readOnly = true)
    List<ControlSchedule> findReadyToRun();
    
    /**
     * Get control schedules ready to be finished
     */
    @Transactional(readOnly = true)
	List<ControlSchedule> findReadyToFinish();
    
    /**
     * Pause a control schedule
     */
    @Transactional
    void updateStatus(Long controlId, String newStatus);
}