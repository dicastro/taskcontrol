package com.qopuir.taskcontrol.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlParamDAO;
import com.qopuir.taskcontrol.dao.ControlScheduleDAO;
import com.qopuir.taskcontrol.dao.ControlScheduleParamDAO;
import com.qopuir.taskcontrol.entities.ControlParamVO;
import com.qopuir.taskcontrol.entities.ControlScheduleParamVO;
import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.entities.enums.ParamName;
import com.qopuir.taskcontrol.service.ControlScheduleService;
import com.qopuir.taskcontrol.workflow.ControlScheduleWorkflow;

@Service
public class ControlScheduleServiceImpl implements ControlScheduleService {
	@Autowired
    ControlScheduleDAO controlScheduleDAO;
	@Autowired
    ControlParamDAO controlParamDAO;
	@Autowired
    ControlScheduleParamDAO controlScheduleParamDAO;
	
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
	public ControlScheduleVO findById(Long controlScheduleId) {
		return controlScheduleDAO.findById(controlScheduleId);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlScheduleVO> findReadyToStart() {
		List<ControlScheduleVO> foundControlSchedules = controlScheduleDAO.findByStatusListWithinPeriod(Arrays.asList(ControlScheduleStatus.PENDING, ControlScheduleStatus.RUNNING));
		
		return foundControlSchedules;
    }
	
	@Override
    @Transactional(readOnly = true)
    public List<ControlScheduleVO> findReadyToStop() {
		return union(controlScheduleDAO.findByStatusListWithinPeriod(Arrays.asList(ControlScheduleStatus.PAUSED,ControlScheduleStatus.FINISHED)),
				controlScheduleDAO.findByStatusListAfterPeriod(Arrays.asList(ControlScheduleStatus.PAUSED, ControlScheduleStatus.RUNNING, ControlScheduleStatus.FINISHED)));
    }
	
	@Override
	@Transactional
	public void start(Long controlScheduleId) {
		executeAction(controlScheduleId, ControlScheduleAction.START);
	}
	
	@Override
	@Transactional
	public void stop(Long controlScheduleId) {
		executeAction(controlScheduleId, ControlScheduleAction.STOP);
	}
	
	@Override
	@Transactional
	public void executeAction(Long controlScheduleId, ControlScheduleAction action) {
		ControlScheduleVO controlSchedule = controlScheduleDAO.findById(controlScheduleId);
		
		if (controlSchedule != null && controlScheduleWorkflow.checkAction(controlSchedule, action)) {
			controlScheduleDAO.updateStatus(controlScheduleId, controlScheduleWorkflow.getNextStatus(controlSchedule.getStatus(), action));
		}
	}
	
	private List<ControlScheduleVO> union(final List<ControlScheduleVO> list1, final List<ControlScheduleVO> list2) {
        ArrayList<ControlScheduleVO> result = null;
        
        if (list1 != null) {
        	result = new ArrayList<ControlScheduleVO>(list1);
        } else {
        	result = new ArrayList<ControlScheduleVO>();
        }
        
        if (list2 != null) {
        	result.addAll(list2);
        }
        
        return result;
    }
	
	@Override
	public Map<ParamName, ControlScheduleParamVO> getControlParams(ControlName controlName, Long controlScheduleId) {
		Map<ParamName, ControlScheduleParamVO> controlParamsMap = new HashMap<ParamName, ControlScheduleParamVO>();

		List<ControlParamVO> controlParams = controlParamDAO.listControlParams(controlName);
		
		for (final ControlParamVO controlParam : controlParams) {
			controlParamsMap.put(controlParam.getParamName(), new ControlScheduleParamVO().setControlScheduleId(controlScheduleId).setControlName(controlName).setParamName(controlParam.getParamName()).setValue(controlParam.getDefaultValue()));
		}
		
		List<ControlScheduleParamVO> controlScheduleParams = controlScheduleParamDAO.listControlParams(controlScheduleId);
		
		for (ControlScheduleParamVO controlScheduleParam : controlScheduleParams) {
			controlParamsMap.put(controlScheduleParam.getParamName(), controlScheduleParam);
		}
		
		return controlParamsMap;
	}
}