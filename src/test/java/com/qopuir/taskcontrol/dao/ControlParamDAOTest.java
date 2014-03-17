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
import com.qopuir.taskcontrol.common.BooleanBinder;
import com.qopuir.taskcontrol.common.CommonOperations;
import com.qopuir.taskcontrol.common.EnumTypeBinder;
import com.qopuir.taskcontrol.entities.ControlParamVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ParamName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ControlParamDAOTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	ControlParamDAO controlParamDAO;
	
	@Before
	public void prepare() throws Exception {
		Operation operation = sequenceOf(
				CommonOperations.DELETE_ALL,
				CommonOperations.INSERT_REFERENCE_DATA,
				insertInto("CONTROL_PARAMS")
						.columns("CONTROL_NAME", "PARAM_NAME", "REQUIRED", "DESCRIPTION")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "PARAM_NAME")
						.withBinder(new BooleanBinder(), "REQUIRED")
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_FROM).column("REQUIRED", Boolean.TRUE).column("DESCRIPTION", "Contiene la direccion de email del remitente de los emails que se envíen por el control").end()
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_SUBJECT).column("REQUIRED", Boolean.TRUE).column("DESCRIPTION", "Contiene el asunto de los de los emails que se envíen por el control").end()
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_TEMPLATE).column("REQUIRED", Boolean.TRUE).column("DESCRIPTION", "Contiene la plantilla de los emails que se envíen por el control").end()
						.useMetadata(false)
						.build()
		);

		DbSetup dbSetup = new DbSetup(new DataSourceDestination(transactionAwareDataSource), operation);

		dbSetupTracker.launchIfNecessary(dbSetup);
	}
	
	@Test
	@Transactional
	public void listUserControls() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlParamVO> allControlParams = controlParamDAO.listControlParams(ControlName.PROJECTION_SEMANAL);

        Assert.assertEquals(3, allControlParams.size());
        
        for (ControlParamVO controlParam : allControlParams) {
        	Assert.assertTrue(controlParam.getRequired());
        }
	}
}