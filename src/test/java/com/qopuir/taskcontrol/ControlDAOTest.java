package com.qopuir.taskcontrol;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.dao.ControlDAO;
import com.qopuir.taskcontrol.model.Control;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/database-context.xml"})
public class ControlDAOTest {
	@Autowired
	ControlDAO controlDAO;
	
	@Test
    public void testList() throws Exception {
        List<Control> result = controlDAO.list();

        Assert.assertEquals(2, result.size());
    }
}