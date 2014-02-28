package com.qopuir.taskcontrol.services.impl;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record2;
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
        
        return dsl.select(controlsTable.NAME, controlsTable.DESCRIPTION).from(controlsTable).orderBy(controlsTable.NAME).fetch(new RecordMapper<Record2<String, String>, Control>() {
			@Override
			public Control map(Record2<String, String> record) {
				return new Control(record.value1(), record.value2());
			}
        });
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Control> listUserControls(String username) {
		Controls controlsTable = Tables.CONTROLS.as("c");
        
        return dsl.select(controlsTable.NAME, controlsTable.DESCRIPTION).from(controlsTable).join(Tables.USERS_CONTROLS).on(controlsTable.NAME.eq(Tables.USERS_CONTROLS.CONTROL_NAME)).where(Tables.USERS_CONTROLS.USER_USERNAME.eq(username)).orderBy(controlsTable.NAME).fetch(new RecordMapper<Record2<String, String>, Control>() {
			@Override
			public Control map(Record2<String, String> record) {
				return new Control(record.value1(), record.value2());
			}
        });
	}
}