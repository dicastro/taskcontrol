package com.qopuir.taskcontrol.services.impl;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.Control;
import com.qopuir.taskcontrol.model.h2.Tables;
import com.qopuir.taskcontrol.model.h2.tables.Controls;
import com.qopuir.taskcontrol.services.ControlService;

public class ControlServiceImpl implements ControlService {
	@Autowired
    DSLContext dsl;
	
	@Override
	@Transactional(readOnly = true)
	public List<Control> list() {
		Controls controlsTable = Tables.CONTROLS.as("c");
        
        return dsl.select(controlsTable.ID, controlsTable.NAME, controlsTable.DESCRIPTION).from(controlsTable).orderBy(controlsTable.NAME).fetch(new RecordMapper<Record3<Long, String, String>, Control>() {
			@Override
			public Control map(Record3<Long, String, String> record) {
				return new Control(record.value1(), record.value2(), record.value3());
			}
        });
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Control> listUserControls(Long userId) {
		Controls controlsTable = Tables.CONTROLS.as("c");
        
        return dsl.select(controlsTable.ID, controlsTable.NAME, controlsTable.DESCRIPTION).from(controlsTable).join(Tables.USERS_CONTROLS).on(controlsTable.ID.eq(Tables.USERS_CONTROLS.CONTROL_ID)).where(Tables.USERS_CONTROLS.USER_ID.eq(userId)).orderBy(controlsTable.NAME).fetch(new RecordMapper<Record3<Long, String, String>, Control>() {
			@Override
			public Control map(Record3<Long, String, String> record) {
				return new Control(record.value1(), record.value2(), record.value3());
			}
        });
	}
}