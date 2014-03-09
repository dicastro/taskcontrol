package com.qopuir.taskcontrol.dao;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.qopuir.taskcontrol.common.CommonOperations;
import com.qopuir.taskcontrol.common.EnumTypeBinder;
import com.qopuir.taskcontrol.entities.UserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserDAOTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	UserDAO userDAO;
	
	@Before
	public void prepare() throws Exception {
		Operation operation = sequenceOf(
				CommonOperations.DELETE_ALL,
				CommonOperations.INSERT_REFERENCE_DATA,
				insertInto("USER_CONTROLS")
						.columns("USER_USERNAME", "CONTROL_NAME")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME")
						.values("d_castro", ControlName.PROJECTION_SEMANAL)
						.useMetadata(false)
						.build()
		);

		DbSetup dbSetup = new DbSetup(new DataSourceDestination(transactionAwareDataSource), operation);

		dbSetupTracker.launchIfNecessary(dbSetup);
	}
	
	@Test
	@Transactional
    public void list() {
		dbSetupTracker.skipNextLaunch();
		
        List<UserVO> allUsers = userDAO.list();

        Assert.assertEquals(1, allUsers.size());
    }
	
	@Test
	@Transactional
    public void create() {
		userDAO.create(new UserVO("demo1", "demo1").setEmail("demo1@sopragroup.com"));
		
		List<UserVO> allUsers = userDAO.list();

        Assert.assertEquals(2, allUsers.size());
        
        for (UserVO user : allUsers) {
        	if (user.getUsername().equals("demo1")) {
        		Assert.assertEquals("demo1", user.getPassword());
        		Assert.assertEquals("demo1@sopragroup.com", user.getEmail());
        	}
        }
    }
	
	@Test
	@Transactional
    public void listContolUsers_existingUsers() {
		dbSetupTracker.skipNextLaunch();
		
        List<UserVO> controlUsers = userDAO.listControlUsers(ControlName.PROJECTION_SEMANAL);

        Assert.assertEquals(1, controlUsers.size());
    }
	
	@Test
	@Transactional
    public void listContolUsers_nonExistingUsers() {
		dbSetupTracker.skipNextLaunch();
		
        List<UserVO> controlUsers = userDAO.listControlUsers(ControlName.PROJECTION_MENSUAL);

        Assert.assertEquals(0, controlUsers.size());
    }
}