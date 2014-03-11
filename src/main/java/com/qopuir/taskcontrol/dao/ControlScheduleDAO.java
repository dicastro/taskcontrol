package com.qopuir.taskcontrol.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

public interface ControlScheduleDAO {
	/**
     * Create a control schedule
     */
    void create(ControlScheduleVO controlSchedule);
    
    /**
     * Get list of control schedules
     */
    List<ControlScheduleVO> list();
    
    /**
     * Get a control schedule by id
     */
	ControlScheduleVO findById(@Param("controlId") Long controlId);
    
	/**
     * Get control schedules by status
     */
    List<ControlScheduleVO> findByStatus(@Param("status") ControlScheduleStatus status);
    
    /**
     * Get control schedules in any of status in the list
     */
    List<ControlScheduleVO> findByStatusList(@Param("statusList") List<ControlScheduleStatus> statusList);
	
    /**
     * Get control schedules by status between start date and end date
     */
    List<ControlScheduleVO> findByStatusWithinPeriod(@Param("status") ControlScheduleStatus status);
    
    /**
     * Get control schedules in any of status in the list between start date and end date
     */
    List<ControlScheduleVO> findByStatusListWithinPeriod(@Param("statusList") List<ControlScheduleStatus> statusList);
    
    /**
     * Get control schedules by status after end date
     */
    List<ControlScheduleVO> findByStatusAfterPeriod(@Param("status") ControlScheduleStatus status);
    
    /**
     * Get control schedules in any of status in the list after end date
     */
    List<ControlScheduleVO> findByStatusListAfterPeriod(@Param("statusList") List<ControlScheduleStatus> statusList);
    
    /**
     * Pause a control schedule
     */
    void updateStatus(@Param("controlId") Long controlId, @Param("newStatus") ControlScheduleStatus newStatus);
}