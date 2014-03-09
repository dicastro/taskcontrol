package com.qopuir.taskcontrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlScheduleDAO;
import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.service.ControlScheduleService;
import com.qopuir.taskcontrol.workflow.ControlScheduleWorkflow;

@Service
public class ControlScheduleServiceImpl implements ControlScheduleService {
	@Autowired
    ControlScheduleDAO controlScheduleDAO;
	
	@Autowired
	ControlScheduleWorkflow controlScheduleWorkflow;
	
	@Override
	@Transactional
	public Long create(ControlScheduleVO controlScheduleVO) {
		controlScheduleDAO.create(controlScheduleVO);
		return controlScheduleVO.getId();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ControlScheduleVO> list() {
        return controlScheduleDAO.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ControlScheduleVO findById(Long controlId) {
		return controlScheduleDAO.findById(controlId);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlScheduleVO> findReadyToRun() {
		List<ControlScheduleVO> foundControlSchedules = controlScheduleDAO.findByStatus(ControlScheduleStatus.PENDING);
		
		if (foundControlSchedules != null) {
			foundControlSchedules.addAll(controlScheduleDAO.findByStatus(ControlScheduleStatus.RUNNING));
		} else {
			foundControlSchedules = controlScheduleDAO.findByStatus(ControlScheduleStatus.RUNNING);
		}
    	
		return foundControlSchedules;
    }
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlScheduleVO> findReadyToFinish() {
    	return controlScheduleDAO.findByStatus(ControlScheduleStatus.FINISHED);
    }
	
	@Override
	@Transactional
	public void start(Long controlId) {
		ControlScheduleVO controlSchedule = controlScheduleDAO.findById(controlId);
		
		if (controlSchedule != null && controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.RUNNING)) {
			controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.RUNNING);
		}
	}
	
	@Override
	@Transactional
	public void start(List<ControlScheduleVO> controlSchedules) {
		for (ControlScheduleVO controlSchedule : controlSchedules) {
			if (controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.RUNNING)) {
				controlScheduleDAO.updateStatus(controlSchedule.getId(), ControlScheduleStatus.RUNNING);
			}
		}
	}
	
	@Override
	@Transactional
	public void pause(Long controlId) {
		ControlScheduleVO controlSchedule = controlScheduleDAO.findById(controlId);
		
		if (controlSchedule != null && controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.PAUSED)) {
			controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.PAUSED);
		}
	}
	
	@Override
	@Transactional
	public void resume(Long controlId) {
		ControlScheduleVO controlSchedule = findById(controlId);
		
		if (controlSchedule != null && controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.RUNNING)) {
        	controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.RUNNING);
    	}
	}
	
	@Override
	@Transactional
	public void finish(Long controlId) {
		ControlScheduleVO controlSchedule = controlScheduleDAO.findById(controlId);
		
		if (controlSchedule != null && controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.FINISHED)) {
			controlScheduleDAO.updateStatus(controlId, ControlScheduleStatus.FINISHED);
		}
	}
	
	@Override
	@Transactional
	public void finish(List<ControlScheduleVO> controlSchedules) {
		for (ControlScheduleVO controlSchedule : controlSchedules) {
			if (controlScheduleWorkflow.checkAction(controlSchedule.getStatus(), ControlScheduleStatus.FINISHED)) {
				controlScheduleDAO.updateStatus(controlSchedule.getId(), ControlScheduleStatus.FINISHED);
			}
		}
	}
}