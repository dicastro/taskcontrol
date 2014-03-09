package com.qopuir.taskcontrol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlDAO;
import com.qopuir.taskcontrol.entities.ControlVO;
import com.qopuir.taskcontrol.service.ControlService;

@Service
public class ControlServiceImpl implements ControlService {
	@Autowired
    ControlDAO controlDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<ControlVO> list() {
		return controlDAO.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ControlVO> listUserControls(String username) {
		return controlDAO.listUserControls(username);
	}
}