package com.qopuir.taskcontrol;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.dao.ControlScheduleDAO;
import com.qopuir.taskcontrol.model.ControlSchedule;
import com.qopuir.taskcontrol.model.ControlScheduleStatus;
import com.qopuir.taskcontrol.model.TypeControl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/database-context.xml"})
public class ControlScheduleDAOTest {
	@Autowired
	ControlScheduleDAO controlScheduleDAO;
	
	@Test
    public void testList() throws Exception {
        List<ControlSchedule> result = controlScheduleDAO.list();

        Assert.assertEquals(0, result.size());
    }
	
	@Test
    public void testCreateControlSchedule() throws Exception {
		List<ControlSchedule> result = controlScheduleDAO.findReadyToRun();
        Assert.assertEquals(0, result.size());
        
        result = controlScheduleDAO.findReadyToFinish();
        Assert.assertEquals(0, result.size());
        
        DateTime start = new DateTime().withField(DateTimeFieldType.hourOfDay(), 0).withField(DateTimeFieldType.minuteOfDay(), 0);
        
        DateTime end = new DateTime().plusDays(5).withField(DateTimeFieldType.hourOfDay(), 0).withField(DateTimeFieldType.minuteOfDay(), 0).withField(DateTimeFieldType.minuteOfDay(), 0);
        
		Long controlScheduleId = controlScheduleDAO.create(new Timestamp(start.getMillis()), new Timestamp(end.getMillis()), "cron", TypeControl.PROJECTION_SEMANAL.toString());
		
		result = controlScheduleDAO.list();

        Assert.assertEquals(1, result.size());
        
        Assert.assertEquals(controlScheduleId, result.get(0).getId());
        Assert.assertEquals(ControlScheduleStatus.PENDING, result.get(0).getStatus());
        Assert.assertEquals("cron", result.get(0).getCron());
        Assert.assertEquals(start.getDayOfMonth(), result.get(0).getStart().getDayOfMonth());
        Assert.assertEquals(start.getHourOfDay(), result.get(0).getStart().getHourOfDay());
        Assert.assertEquals(end.getDayOfMonth(), result.get(0).getEnd().getDayOfMonth());
        Assert.assertEquals(end.getMinuteOfHour(), result.get(0).getEnd().getMinuteOfHour());
        
        result = controlScheduleDAO.findReadyToRun();
        Assert.assertEquals(1, result.size());
        
        result = controlScheduleDAO.findReadyToFinish();
        Assert.assertEquals(0, result.size());
        
        controlScheduleDAO.updateStatus(controlScheduleId, ControlScheduleStatus.FINISHED.toString());
        
        result = controlScheduleDAO.findReadyToRun();
        Assert.assertEquals(0, result.size());
        
        controlScheduleDAO.create(new Timestamp(start.minusDays(10).getMillis()), new Timestamp(end.minusDays(6).getMillis()), "cron", TypeControl.PROJECTION_SEMANAL.toString());
        
        result = controlScheduleDAO.findReadyToFinish();
        Assert.assertEquals(1, result.size());
    }
}