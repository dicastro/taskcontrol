package com.qopuir.taskcontrol.dao;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import java.util.List;

import javax.sql.DataSource;

import org.joda.time.LocalDateTime;
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
import com.qopuir.taskcontrol.common.LocalDateTimeBinder;
import com.qopuir.taskcontrol.entities.ControlScheduleParamVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;
import com.qopuir.taskcontrol.entities.enums.ParamName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ControlScheduleParamDAOTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	ControlScheduleParamDAO controlScheduleParamDAO;
	
	@Before
	public void prepare() throws Exception {
		Operation operation = sequenceOf(
				CommonOperations.DELETE_ALL,
				CommonOperations.INSERT_REFERENCE_DATA,
				insertInto("CONTROL_PARAMS")
						.columns("CONTROL_NAME", "PARAM_NAME", "DESCRIPTION")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "PARAM_NAME")
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_FROM).column("DESCRIPTION", "Contiene la direccion de email del remitente de los emails que se envíen por el control").end()
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_SUBJECT).column("DESCRIPTION", "Contiene el asunto de los de los emails que se envíen por el control").end()
						.row().column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_TEMPLATE).column("DESCRIPTION", "Contiene la plantilla de los emails que se envíen por el control").end()
						.useMetadata(false)
						.build(),
				insertInto("CONTROL_SCHEDULES")
						.columns("ID", "START_DATE", "END_DATE", "CRON", "CONTROL_NAME", "SCHEDULE_STATUS")
						.withBinder(new LocalDateTimeBinder(), "START_DATE", "END_DATE")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "SCHEDULE_STATUS")
						.row().column("ID", 1L).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "*/10 * * * * ?").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.RUNNING).end()
						.useMetadata(false)
						.build(),
				insertInto("CONTROL_SCHEDULE_PARAMS")
						.columns("CONTROL_SCHEDULE_ID", "CONTROL_NAME", "PARAM_NAME", "PARAM_VALUE")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "PARAM_NAME")
						.row().column("CONTROL_SCHEDULE_ID", 1L).column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_FROM).column("PARAM_VALUE", "carlos.cordero@sopragroup.com").end()
						.row().column("CONTROL_SCHEDULE_ID", 1L).column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_SUBJECT).column("PARAM_VALUE", "Projection!!").end()
						.row().column("CONTROL_SCHEDULE_ID", 1L).column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("PARAM_NAME", ParamName.MAIL_TEMPLATE).column("PARAM_VALUE", "projection-semanal.html").end()
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
		
        List<ControlScheduleParamVO> allControlScheduleParams = controlScheduleParamDAO.listControlParams(1L);

        Assert.assertEquals(3, allControlScheduleParams.size());
	}
}