package com.qopuir.taskcontrol.workflow;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.entities.enums.ControlScheduleStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/database-context.xml", "classpath:spring/mybatis-context.xml", "classpath:spring/application-context.xml" })
public class ControlScheduleWorkflowTest {
	@Autowired
	ControlScheduleWorkflow controlScheduleWorkflow;
	
	@Test
	public void checkAction_fromPending_toRunning() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PENDING, ControlScheduleStatus.RUNNING);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromPending_toPaused() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PENDING, ControlScheduleStatus.PAUSED);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromPending_toFinished() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PENDING, ControlScheduleStatus.FINISHED);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromPending_toPending() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PENDING, ControlScheduleStatus.PENDING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromRunning_toPending() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.RUNNING, ControlScheduleStatus.PENDING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromRunning_toPaused() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.RUNNING, ControlScheduleStatus.PAUSED);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromRunning_toFinished() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.RUNNING, ControlScheduleStatus.FINISHED);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromRunning_toRunning() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.RUNNING, ControlScheduleStatus.RUNNING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromPaused_toPending() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PAUSED, ControlScheduleStatus.PENDING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromPaused_toRunnig() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PAUSED, ControlScheduleStatus.RUNNING);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromPaused_toFinished() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PAUSED, ControlScheduleStatus.FINISHED);
		
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void checkAction_fromPaused_toPaused() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.PAUSED, ControlScheduleStatus.PAUSED);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromFinished_toPending() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.FINISHED, ControlScheduleStatus.PENDING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromFinished_toRunning() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.FINISHED, ControlScheduleStatus.RUNNING);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromFinished_toPaused() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.FINISHED, ControlScheduleStatus.PAUSED);
		
		Assert.assertFalse(checkResult);
	}
	
	@Test
	public void checkAction_fromFinished_toFinished() {
		boolean checkResult = controlScheduleWorkflow.checkAction(ControlScheduleStatus.FINISHED, ControlScheduleStatus.FINISHED);
		
		Assert.assertFalse(checkResult);
	}
}