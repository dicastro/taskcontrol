package com.qopuir.taskcontrol;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.model.Control;
import com.qopuir.taskcontrol.services.ControlService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/database-context.xml"})
public class ControlTest {
	@Autowired
	ControlService controlService;
	
	@Test
    public void testList() throws Exception {
        List<Control> result = controlService.list();

        Assert.assertEquals(2, result.size());
    }
}