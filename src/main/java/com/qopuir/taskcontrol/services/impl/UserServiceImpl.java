package com.qopuir.taskcontrol.services.impl;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record2;
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
	public void create(String userName, String password, String email) {
		dsl.insertInto(Tables.USERS).set(Tables.USERS.USERNAME, userName).set(Tables.USERS.PASSWORD, password).set(Tables.USERS.EMAIL, email).execute();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> list() {
		Users usersTable = Tables.USERS.as("u");
        
        return dsl.select(usersTable.USERNAME, usersTable.EMAIL).from(usersTable).orderBy(usersTable.USERNAME).fetch(new RecordMapper<Record2<String, String>, User>() {
			@Override
			public User map(Record2<String, String> record) {
				User user = new User(record.value1());
				user.setEmail(record.value2());
				
				return user;
			}
        });
	}
	
	@Override
	@Transactional
	public void addUserControl(String username, String controlName) {
        dsl.insertInto(Tables.USERS_CONTROLS.asTable()).set(Tables.USERS_CONTROLS.USER_USERNAME, username).set(Tables.USERS_CONTROLS.CONTROL_NAME, controlName).execute();
	}
	
	@Override
	@Transactional
	public void removeUserControl(String username, String controlName) {
        dsl.delete(Tables.USERS_CONTROLS.asTable()).where(Tables.USERS_CONTROLS.USER_USERNAME.eq(username)).and(Tables.USERS_CONTROLS.CONTROL_NAME.eq(controlName)).execute();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<User> listControlUsers(String controlName) {
		Users usersTable = Tables.USERS.as("u");
        
        return dsl.select(usersTable.USERNAME, usersTable.EMAIL).from(usersTable).join(Tables.USERS_CONTROLS).on(usersTable.USERNAME.eq(Tables.USERS_CONTROLS.USER_USERNAME)).where(Tables.USERS_CONTROLS.CONTROL_NAME.eq(controlName)).orderBy(usersTable.USERNAME).fetch(new RecordMapper<Record2<String, String>, User>() {
			@Override
			public User map(Record2<String, String> record) {
				User user = new User(record.value1());
				user.setEmail(record.value2());
				
				return user;
			}
        });
	}
}