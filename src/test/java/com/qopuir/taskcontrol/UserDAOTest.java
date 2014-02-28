package com.qopuir.taskcontrol;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.dao.UserDAO;
import com.qopuir.taskcontrol.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/database-context.xml"})
public class UserDAOTest {
	@Autowired
	UserDAO userDAO;
	
	@Test
    public void testList() throws Exception {
        List<User> result = userDAO.list();

        Assert.assertEquals(1, result.size());
    }
	
	@Test
    public void testUserCreation() throws Exception {
		userDAO.create("demo1", "demo1", "demo1@controltask.com");
		
		List<User> result = userDAO.list();

        Assert.assertEquals(2, result.size());
              
        Assert.assertEquals(result.get(1).getUsername(), "demo1");
        Assert.assertNull(result.get(1).getPassword());
        Assert.assertEquals(result.get(1).getEmail(), "demo1@controltask.com");
    }
}