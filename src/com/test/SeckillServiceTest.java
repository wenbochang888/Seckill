package com.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.entity.Seckill;
import com.service.SeckillService;

@ContextConfiguration({ "classpath:config/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SeckillServiceTest {


	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() throws Exception{
		
		List<Seckill> seckills = seckillService.getSeckillList();
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testGetById() {
		
		Seckill seckill = seckillService.getById(1000);
		System.out.println(seckill);
	}
	
	/**
	 * ³É¹¦£ºExposer [exposed=true, md5=44493adfd57b1532fe6e16a0d5d686c8, 
	 * seckillId=1000, now=0, start=0, end=0]
	 * 
	 * Ê§°Ü£ºExposer [exposed=false, md5=null, seckillId=1001, 
	 * now=1515816207942, start=1515686400000, end=1515772800000]
	 */
	@Test
	public void testExportSeckillUrl() {
		
		long id = 1001L;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		System.out.println(exposer);
	}
	
	@Test
	public void testExecuteSeckill() {
		
		long seckillId = 1000L;
		long phone = 13725183521L;
		String md5 = seckillService.exportSeckillUrl(seckillId).getMd5();
		SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
		System.out.println(seckillExecution);
	}
	
	@Test
	public void testExecuteSeckillProcedure() throws Exception {
		long seckillId = 1001;
		long phone = 13631231234L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExecution execution = 
					seckillService.executeSeckillProduce(seckillId, phone, md5);
			System.out.println(execution);
		}
	}
}











