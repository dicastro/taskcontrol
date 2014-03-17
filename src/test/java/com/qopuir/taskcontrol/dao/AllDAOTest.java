package com.qopuir.taskcontrol.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ControlDAOTest.class, ControlParamDAOTest.class, ControlScheduleDAOTest.class, ControlUserDAOTest.class, UserDAOTest.class })
public class AllDAOTest {
}