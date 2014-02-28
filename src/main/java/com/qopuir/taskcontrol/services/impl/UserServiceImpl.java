package com.qopuir.taskcontrol.services.impl;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.qopuir.taskcontrol.model.User;
import com.qopuir.taskcontrol.model.h2.Tables;
import com.qopuir.taskcontrol.model.h2.tables.Users;
import com.qopuir.taskcontrol.services.UserService;

public class UserServiceImpl implements UserService {
	@Autowired
    DSLContext dsl;
	
	@Override
	@Transactional
	public void create(String userName, String password) {
		dsl.insertInto(Tables.USERS).set(Tables.USERS.USERNAME, userName).set(Tables.USERS.PASSWORD, password).execute();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> list() {
		Users usersTable = Tables.USERS.as("u");
        
        return dsl.select(usersTable.ID, usersTable.USERNAME, usersTable.PASSWORD).from(usersTable).orderBy(usersTable.USERNAME).fetch(new RecordMapper<Record3<Long, String, String>, User>() {
			@Override
			public User map(Record3<Long, String, String> record) {
				return new User(record.value1(), record.value2(), record.value3());
			}
        });
	}
	
	@Override
	@Transactional
	public void addUserControl(Long userId, Long controlId) {
        dsl.insertInto(Tables.USERS_CONTROLS.asTable()).set(Tables.USERS_CONTROLS.USER_ID, userId).set(Tables.USERS_CONTROLS.CONTROL_ID, controlId).execute();
	}
	
	@Override
	@Transactional
	public void removeUserControl(Long userId, Long controlId) {
        dsl.delete(Tables.USERS_CONTROLS.asTable()).where(Tables.USERS_CONTROLS.USER_ID.eq(userId)).and(Tables.USERS_CONTROLS.CONTROL_ID.eq(controlId)).execute();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> listControlUsers(Long controlId) {
		Users usersTable = Tables.USERS.as("u");
        
        return dsl.select(usersTable.ID, usersTable.USERNAME).from(usersTable).join(Tables.USERS_CONTROLS).on(usersTable.ID.eq(Tables.USERS_CONTROLS.USER_ID)).where(Tables.USERS_CONTROLS.CONTROL_ID.eq(controlId)).orderBy(usersTable.USERNAME).fetch(new RecordMapper<Record2<Long, String>, User>() {
			@Override
			public User map(Record2<Long, String> record) {
				return new User(record.value1(), record.value2());
			}
        });
	}
}