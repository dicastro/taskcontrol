package com.qopuir.taskcontrol.service;

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
import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlName;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml", "classpath:spring/application-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ControlScheduleServiceTest {
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
	private static final Long PENDING_BEFORE_PERIOD = 1L;
	private static final Long PAUSED_WITHIN_PERIOD = 2L;
	private static final Long RUNNING_WITHIN_PERIOD = 3L;
	private static final Long FINISHED_AFTER_PERIOD = 4L;
	
	@Autowired
	private DataSource transactionAwareDataSource;
	
	@Autowired
	ControlScheduleService controlScheduleService;
	
	@Before
	public void prepare() throws Exception {
		Operation operation = sequenceOf(
				CommonOperations.DELETE_ALL,
				CommonOperations.INSERT_REFERENCE_DATA,
				insertInto("CONTROL_SCHEDULES")
						.columns("ID", "START_DATE", "END_DATE", "CRON", "CONTROL_NAME", "SCHEDULE_STATUS")
						.withBinder(new LocalDateTimeBinder(), "START_DATE", "END_DATE")
						.withBinder(new EnumTypeBinder(), "CONTROL_NAME", "SCHEDULE_STATUS")
						.row().column("ID", PENDING_BEFORE_PERIOD).column("START_DATE", new LocalDateTime().plusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.PENDING).end()
						.row().column("ID", PAUSED_WITHIN_PERIOD).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.RUNNING).end()
						.row().column("ID", RUNNING_WITHIN_PERIOD).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime(2014, 12, 31, 12, 0, 0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.PAUSED).end()
						.row().column("ID", FINISHED_AFTER_PERIOD).column("START_DATE", new LocalDateTime(2014, 01, 01, 12, 0, 0)).column("END_DATE", new LocalDateTime().minusDays(1).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).column("CRON", "cron").column("CONTROL_NAME", ControlName.PROJECTION_SEMANAL).column("SCHEDULE_STATUS", ControlScheduleStatus.FINISHED).end()
						.useMetadata(false)
						.build()
		);

		DbSetup dbSetup = new DbSetup(new DataSourceDestination(transactionAwareDataSource), operation);

		dbSetupTracker.launchIfNecessary(dbSetup);
	}
	
	@Test
	@Transactional
    public void findReadyToStart() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleService.findReadyToStart();

        Assert.assertEquals(1, result.size());
    }
	
	@Test
	@Transactional
    public void findReadyToStop() {
		dbSetupTracker.skipNextLaunch();
		
        List<ControlScheduleVO> result = controlScheduleService.findReadyToStop();

        Assert.assertEquals(2, result.size());
    }
	
	@Test
	@Transactional
    public void execute_startControlSchedulePendingBeforePeriod_statusRemainsPending() {
		controlScheduleService.executeAction(PENDING_BEFORE_PERIOD, ControlScheduleAction.START);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PENDING_BEFORE_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.PENDING, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_stopControlSchedulePendingBeforePeriod_statusChangesToFinished() {
		controlScheduleService.executeAction(PENDING_BEFORE_PERIOD, ControlScheduleAction.STOP);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PENDING_BEFORE_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_pauseControlSchedulePendingBeforePeriod_statusRemainsPending() {
		controlScheduleService.executeAction(PENDING_BEFORE_PERIOD, ControlScheduleAction.PAUSE);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PENDING_BEFORE_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.PENDING, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_startControlScheduleRunningWithinPeriod_statusRemainsRunning() {
		controlScheduleService.executeAction(PAUSED_WITHIN_PERIOD, ControlScheduleAction.START);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PAUSED_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.RUNNING, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_stopControlScheduleRunningWithinPeriod_statusChangesToFinished() {
		controlScheduleService.executeAction(PAUSED_WITHIN_PERIOD, ControlScheduleAction.STOP);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PAUSED_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_pauseControlScheduleRunningWithinPeriod_statusChangesToPaused() {
		controlScheduleService.executeAction(PAUSED_WITHIN_PERIOD, ControlScheduleAction.PAUSE);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(PAUSED_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.PAUSED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_startControlSchedulePausedWithinPeriod_statusChangesToRunning() {
		controlScheduleService.executeAction(RUNNING_WITHIN_PERIOD, ControlScheduleAction.START);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(RUNNING_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.RUNNING, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_stopControlSchedulePausedWithinPeriod_statusChangesToFinished() {
		controlScheduleService.executeAction(RUNNING_WITHIN_PERIOD, ControlScheduleAction.STOP);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(RUNNING_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_pauseControlSchedulePausedWithinPeriod_statusChangesToPaused() {
		controlScheduleService.executeAction(RUNNING_WITHIN_PERIOD, ControlScheduleAction.PAUSE);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(RUNNING_WITHIN_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.PAUSED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_startControlScheduleFinishedAfterPeriod_statusRemainsFinished() {
		controlScheduleService.executeAction(FINISHED_AFTER_PERIOD, ControlScheduleAction.START);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(FINISHED_AFTER_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_stopControlScheduleFinishedAfterPeriod_statusRemainsFinished() {
		controlScheduleService.executeAction(FINISHED_AFTER_PERIOD, ControlScheduleAction.STOP);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(FINISHED_AFTER_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
	
	@Test
	@Transactional
    public void execute_pauseControlScheduleFinishedAfterPeriod_statusRemainsFinished() {
		controlScheduleService.executeAction(FINISHED_AFTER_PERIOD, ControlScheduleAction.PAUSE);

		ControlScheduleVO controlSchedule = controlScheduleService.findById(FINISHED_AFTER_PERIOD);
		
        Assert.assertEquals(ControlScheduleStatus.FINISHED, controlSchedule.getStatus());
    }
}