package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.SuccessKilledDao;
import com.entity.SuccessKilled;
import com.entity.SuccessKilledRecord;

/**
 * @author Chang-pc
 * 
 * Æô¶¯Ê±¼ÓÔØIoCÈÝÆ÷
 */

@ContextConfiguration({ "classpath:config/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SuccessKilledDaoTest {
	
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() throws Exception {
		
		int insertCount = successKilledDao.insertSuccessKilled(1001, 13725183525L);
		System.out.println(insertCount);
	}
	
	@Test
	public void testQueryByIdWithSeckill() throws Exception {
		
		SuccessKilled successKilled = 
				successKilledDao.queryByIdWithSeckill(1001, 13725183525L);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}
	
	@Test
	public void testQueryAllById() throws Exception {
		SuccessKilledRecord successKilledRecord = 
				successKilledDao.queryAllById(1000, 13725183525L);
		System.out.println(successKilledRecord);
	}
}
















