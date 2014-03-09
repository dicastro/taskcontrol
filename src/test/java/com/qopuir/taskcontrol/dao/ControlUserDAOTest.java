package com.qopuir.taskcontrol.dao;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import com.qopuir.taskcontrol.entities.ControlUserVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ControlUserDAOTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	ControlUserDAO controlUserDAO;
	
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
    public void create() {
        controlUserDAO.create(new ControlUserVO().setUserUsername("d_castro").setControlName(ControlName.PROJECTION_MENSUAL));
    }
	
	@Test(expected = DuplicateKeyException.class)
	@Transactional
    public void create_alreadyExistingControl() throws Exception {
		controlUserDAO.create(new ControlUserVO().setUserUsername("d_castro").setControlName(ControlName.PROJECTION_SEMANAL));;
    }
	
	@Test
	@Transactional
    public void remove() throws Exception {
		controlUserDAO.remove(new ControlUserVO().setUserUsername("d_castro").setControlName(ControlName.PROJECTION_SEMANAL));;
    }
}