package com.qopuir.taskcontrol.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Record6;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.dao.ControlScheduleDAO;
import com.qopuir.taskcontrol.model.ControlSchedule;
import com.qopuir.taskcontrol.model.ControlScheduleStatus;
import com.qopuir.taskcontrol.model.TypeControl;
import com.qopuir.taskcontrol.model.h2.Tables;

@Repository
public class ControlScheduleDAOImpl implements ControlScheduleDAO {
	@Autowired
    DSLContext dsl;
	
	@Override
	@Transactional
	public void create(Timestamp start, Timestamp end, String cron, String typeControl) {
		dsl.insertInto(Tables.CONTROLS_SCHEDULE).set(Tables.CONTROLS_SCHEDULE.START, start).set(Tables.CONTROLS_SCHEDULE.END, end).set(Tables.CONTROLS_SCHEDULE.CRON, cron).set(Tables.CONTROLS_SCHEDULE.CONTROL_NAME, typeControl).execute();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ControlSchedule> list() {
        return dsl.select(Tables.CONTROLS_SCHEDULE.ID, Tables.CONTROLS_SCHEDULE.STATUS, Tables.CONTROLS_SCHEDULE.START, Tables.CONTROLS_SCHEDULE.END, Tables.CONTROLS_SCHEDULE.CRON, Tables.CONTROLS_SCHEDULE.CONTROL_NAME).from(Tables.CONTROLS_SCHEDULE).orderBy(Tables.CONTROLS_SCHEDULE.START).fetch(new RecordMapper<Record6<Long, String, Timestamp, Timestamp, String, String>, ControlSchedule>() {
			@Override
			public ControlSchedule map(Record6<Long, String, Timestamp, Timestamp, String, String> record) {
				ControlSchedule controlSchedule = new ControlSchedule();
				controlSchedule.setId(record.value1());
				controlSchedule.setStatus(ControlScheduleStatus.valueOf(record.value2()));
				controlSchedule.setStart(new DateTime(record.value3()));
				controlSchedule.setEnd(new DateTime(record.value4()));
				controlSchedule.setCron(record.value5());
				controlSchedule.setType(TypeControl.valueOf(record.value6()));
				
				return controlSchedule;
			}
        });
	}
	
	@Override
	@Transactional(readOnly = true)
	public ControlSchedule findOne(Long controlId) {
		Record6<Long, String, Timestamp, Timestamp, String, String> record = dsl.select(Tables.CONTROLS_SCHEDULE.ID, Tables.CONTROLS_SCHEDULE.STATUS, Tables.CONTROLS_SCHEDULE.START, Tables.CONTROLS_SCHEDULE.END, Tables.CONTROLS_SCHEDULE.CRON, Tables.CONTROLS_SCHEDULE.CONTROL_NAME).from(Tables.CONTROLS_SCHEDULE).fetchOne();
		
		ControlSchedule controlSchedule = new ControlSchedule();
		controlSchedule.setId(record.value1());
		controlSchedule.setStatus(ControlScheduleStatus.valueOf(record.value2()));
		controlSchedule.setStart(new DateTime(record.value3()));
		controlSchedule.setEnd(new DateTime(record.value4()));
		controlSchedule.setCron(record.value5());
		controlSchedule.setType(TypeControl.valueOf(record.value6()));
		
		return controlSchedule;
	}
	
	@Override
	@Transactional
	public void updateStatus(Long controlId, String newStatus) {
        dsl.update(Tables.CONTROLS_SCHEDULE).set(Tables.CONTROLS_SCHEDULE.STATUS, newStatus).where(Tables.CONTROLS_SCHEDULE.ID.eq(controlId)).execute();
	}
}