package com.qopuir.taskcontrol.dao;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;

import java.util.Arrays;
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
import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ControlScheduleDAOTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	ControlScheduleDAO controlScheduleDAO;
	
	@Before
	public void prepare() throws Exception {
		Operation operation = sequenceOf(
				CommonOperations.DELETE_ALL,
				CommonOperations.INSERT_REFERENCE_DATA,
				insertInto("CONTROL_SCHEDULES")
						.columns("ID", "START_DATE", "END_DATE", "CRON", "CONTROL_NAME", "SCHEDULE_STATUS")
						.withBinder(new LocalDateTimeBinder(), "START_DATE", "END_DATE")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "SCHEDULE_STATUS")
						.row().column("ID", 1L).column("START_DATE", new LocalDateTime().plusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.PENDING).end()
						.row().column("ID", 2L).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.RUNNING).end()
						.row().column("ID", 3L).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.PAUSED).end()
						.row().column("ID", 4L).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime().minusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.FINISHED).end()
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
		
        List<ControlScheduleVO> result = controlScheduleDAO.list();

        Assert.assertEquals(4, result.size());
    }
	
	@Test
	@Transactional
    public void create() {
		ControlScheduleVO newControlSchedule = new ControlScheduleVO().setStart(new LocalDateTime(2014, 01, 01, 12, 0, 0)).setEnd(new LocalDateTime(2014, 12, 31, 12, 0, 0)).setCron("cron").setControlName(ControlName.PROJECTION_MENSUAL);
		controlScheduleDAO.create(newControlSchedule);
		
        Assert.assertNotNull(newControlSchedule.getId());
        
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatus(ControlScheduleStatus.PENDING);

        Assert.assertEquals(2, result.size());
    }
	
	@Test
	@Transactional
    public void findById() {
		dbSetupTracker.skipNextLaunch();
		
        ControlScheduleVO controlSchedule = controlScheduleDAO.findById(1L);

        Assert.assertNotNull(controlSchedule);
        Assert.assertEquals(ControlName.PROJECTION_SEMANAL, controlSchedule.getControlName());
        Assert.assertEquals(ControlScheduleStatus.PENDING, controlSchedule.getStatus());
        Assert.assertEquals("cron", controlSchedule.getCron());
    }
	
	@Test
	@Transactional
    public void findByStatus_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatus(ControlScheduleStatus.FINISHED);

        Assert.assertEquals(1, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusList_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusList(Arrays.asList(ControlScheduleStatus.PENDING, ControlScheduleStatus.FINISHED));

        Assert.assertEquals(2, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusWithinPeriod_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusWithinPeriod(ControlScheduleStatus.RUNNING);

        Assert.assertEquals(1, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusListWithinPeriod_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusListWithinPeriod(Arrays.asList(ControlScheduleStatus.PAUSED, ControlScheduleStatus.RUNNING));

        Assert.assertEquals(2, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusWithinPeriod_nonExistingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusWithinPeriod(ControlScheduleStatus.PENDING);

        Assert.assertEquals(0, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusAfterPeriod_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusAfterPeriod(ControlScheduleStatus.FINISHED);

        Assert.assertEquals(1, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusListAfterPeriod_existingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusListAfterPeriod(Arrays.asList(ControlScheduleStatus.FINISHED));

        Assert.assertEquals(1, result.size());
    }
	
	@Test
	@Transactional
    public void findByStatusAfterPeriod_nonExistingResults() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatusAfterPeriod(ControlScheduleStatus.RUNNING);

        Assert.assertEquals(0, result.size());
    }
	
	@Test
	@Transactional
    public void updateStatus() {
		controlScheduleDAO.updateStatus(1L, ControlScheduleStatus.RUNNING);
		
        List<ControlScheduleVO> result = controlScheduleDAO.findByStatus(ControlScheduleStatus.RUNNING);

        Assert.assertEquals(2, result.size());
        
        result = controlScheduleDAO.findByStatus(ControlScheduleStatus.PENDING);

        Assert.assertEquals(0, result.size());
    }
	
	@Test
	@Transactional
    public void updateStatus_statusIsChangedAndDatesRemainEquals() {
		ControlScheduleVO controlScheduleVO = controlScheduleDAO.findById(1L);
		Assert.assertEquals(ControlScheduleStatus.PENDING, controlScheduleVO.getStatus());
		Assert.assertEquals(new LocalDateTime().plusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0), controlScheduleVO.getStart());
        Assert.assertEquals(new LocalDateTime(2014, 12, 31, 12, 0, 0), controlScheduleVO.getEnd());
		
		controlScheduleDAO.updateStatus(1L, ControlScheduleStatus.RUNNING);
		
		controlScheduleVO = controlScheduleDAO.findById(1L);
		Assert.assertEquals(ControlScheduleStatus.RUNNING, controlScheduleVO.getStatus());
		Assert.assertEquals(new LocalDateTime().plusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0), controlScheduleVO.getStart());
        Assert.assertEquals(new LocalDateTime(2014, 12, 31, 12, 0, 0), controlScheduleVO.getEnd());
    }
}