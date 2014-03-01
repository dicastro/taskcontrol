package com.qopuir.taskcontrol.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlScheduleDAO;
import com.qopuir.taskcontrol.model.ControlSchedule;
import com.qopuir.taskcontrol.model.ControlScheduleStatus;
import com.qopuir.taskcontrol.service.ControlScheduleService;

@Service
public class ControlScheduleServiceImpl implements ControlScheduleService {
	@Autowired
    ControlScheduleDAO controlScheduleDAO;
	
	@Override
	@Transactional
	public Long create(ControlSchedule controlSchedule) {
		return controlScheduleDAO.create(new Timestamp(controlSchedule.getStart().toDateTime().getMillis()), new Timestamp(controlSchedule.getEnd().toDateTime().getMillis()), controlSchedule.getCron(), controlSchedule.getType().toString());
	}

	@Override
	@Transactional(readOnly = true)
	public List<ControlSchedule> list() {
        return controlScheduleDAO.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ControlSchedule findOne(Long controlId) {
		return controlScheduleDAO.findOne(controlId);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlSchedule> findReadyToRun() {
    	return controlScheduleDAO.findReadyToRun();
    }
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlSchedule> findReadyToFinish() {
    	return controlScheduleDAO.findReadyToFinish();
    }
	
	@Override
	@Transactional
	public void pause(Long controlId) {
		ControlSchedule controlSchedule = controlScheduleDAO.findOne(controlId);
		
		if (controlSchedule != null && controlSchedule.getStatus() == ControlScheduleStatus.RUNNING) {
			controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.PAUSED.toString());
		}
	}
	
	@Override
	@Transactional
	public void resume(Long controlId) {
		ControlSchedule controlSchedule = findOne(controlId);
		
		if (controlSchedule != null && controlSchedule.getStatus() == ControlScheduleStatus.PAUSED) {
        	controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.RUNNING.toString());
    	}
	}
	
	@Override
	@Transactional
	public void finish(Long controlId) {
		ControlSchedule controlSchedule = controlScheduleDAO.findOne(controlId);
		
		if (controlSchedule != null && (controlSchedule.getStatus() == ControlScheduleStatus.RUNNING || controlSchedule.getStatus() == ControlScheduleStatus.PAUSED)) {
			controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.FINISHED.toString());
		}
	}
}