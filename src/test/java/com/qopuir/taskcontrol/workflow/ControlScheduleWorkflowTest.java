package com.qopuir.taskcontrol.workflow;

import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.entities.ControlScheduleVO;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleAction;
import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml", "classpath:spring/application-context.xml" })
public class ControlScheduleWorkflowTest {
	@Autowired
	ControlScheduleWorkflow controlScheduleWorkflow;
	
	@Test
	public void checkAction_pendingStartWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.START);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pendingStartBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pendingStartAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pendingStopWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pendingStopBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pendingStopAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pendingPauseWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pendingPauseBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pendingPauseAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PENDING), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_runningStartWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_runningStartBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_runningStartAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_runningStopWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_runningStopBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_runningStopAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_runningPauseWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.PAUSE);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_runningPauseBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_runningPauseAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.RUNNING), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pausedStartWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.START);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pausedStartBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pausedStartAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pausedStopWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pausedStopBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pausedStopAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.STOP);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_pausedPauseWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pausedPauseBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_pausedAfterBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.PAUSED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStartWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStartBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStartAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.START);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStopWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.STOP);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStopBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.STOP);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedStopAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.STOP);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedPauseWithinPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleWithinPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedPauseBeforePeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleBeforePeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_finishedPauseAfterPeriod() {
		boolean checkResult = controlScheduleWorkflow.checkAction(getControlScheduleAfterPeriod(ControlScheduleStatus.FINISHED), ControlScheduleAction.PAUSE);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void getNextStatus_pendingStart() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.PENDING, ControlScheduleAction.START);
		
		Assert.assertEquals(ControlScheduleStatus.RUNNING, nextStatus);
	}
	
	@Test
	public void getNextStatus_pendingStop() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.PENDING, ControlScheduleAction.STOP);
		
		Assert.assertEquals(ControlScheduleStatus.FINISHED, nextStatus);
	}
	
	@Test
	public void getNextStatus_runningStart() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.RUNNING, ControlScheduleAction.START);
		
		Assert.assertNull(nextStatus);
	}
	
	@Test
	public void getNextStatus_runningStop() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.RUNNING, ControlScheduleAction.STOP);
		
		Assert.assertEquals(ControlScheduleStatus.FINISHED, nextStatus);
	}
	
	@Test
	public void getNextStatus_pausedStart() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.PAUSED, ControlScheduleAction.START);
		
		Assert.assertEquals(ControlScheduleStatus.RUNNING, nextStatus);
	}
	
	@Test
	public void getNextStatus_pausedStop() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.PAUSED, ControlScheduleAction.STOP);
		
		Assert.assertEquals(ControlScheduleStatus.FINISHED, nextStatus);
	}
	
	@Test
	public void getNextStatus_finishedStart() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.FINISHED, ControlScheduleAction.START);
		
		Assert.assertNull(nextStatus);
	}
	
	@Test
	public void getNextStatus_finishedStop() {
		ControlScheduleStatus nextStatus = controlScheduleWorkflow.getNextStatus(ControlScheduleStatus.FINISHED, ControlScheduleAction.STOP);
		
		Assert.assertNull(nextStatus);
	}
	
	private ControlScheduleVO getControlScheduleWithinPeriod(ControlScheduleStatus controlScheduleStatus) {
		return new ControlScheduleVO().setStatus(controlScheduleStatus).setStart(new LocalDateTime().withMillisOfSecond(0).minusDays(5)).setEnd(new LocalDateTime().withMillisOfSecond(0).plusDays(5));
	}
	
	private ControlScheduleVO getControlScheduleBeforePeriod(ControlScheduleStatus controlScheduleStatus) {
		return new ControlScheduleVO().setStatus(controlScheduleStatus).setStart(new LocalDateTime().withMillisOfSecond(0).minusDays(15)).setEnd(new LocalDateTime().withMillisOfSecond(0).minusDays(5));
	}
	
	private ControlScheduleVO getControlScheduleAfterPeriod(ControlScheduleStatus controlScheduleStatus) {
		return new ControlScheduleVO().setStatus(controlScheduleStatus).setStart(new LocalDateTime().withMillisOfSecond(0).plusDays(5)).setEnd(new LocalDateTime().withMillisOfSecond(0).plusDays(15));
	}
}