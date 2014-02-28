package com.qopuir.taskcontrol;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qopuir.taskcontrol.model.User;
import com.qopuir.taskcontrol.services.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/database-context.xml"})
public class UserTest {
	@Autowired
	UserService userService;
	
	@Test
    public void testList() throws Exception {
        List<User> result = userService.list();

        Assert.assertEquals(1, result.size());
    }
	
	@Test
    public void testUserCreation() throws Exception {
		userService.create("demo1", "demo1");
		
		List<User> result = userService.list();

        Assert.assertEquals(2, result.size());
              
        Assert.assertEquals(result.get(1).getUsername(), "demo1");
        Assert.assertEquals(result.get(1).getPassword(), "demo1");
    }
}