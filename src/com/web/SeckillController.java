package com.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.dto.SeckillResult;
import com.entity.Seckill;
import com.enums.SeckillStatEnum;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.service.SeckillService;

/**
 * @author wenbochang
 * @date 2018年1月13日
 */

@Controller
//@RequestMapping("seckill") // 模块 所有的url都是 /seckill/{id}/细分 这种形式的
public class SeckillController {

	@Autowired
	private SeckillService seckillService;

	/**
	 * 获取秒杀所有的列表页面
	 * 
	 * 获取参数可以用map也可以用model.addAttrabute 但自己习惯用了map而已
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Map<String, Object> map) {

		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		map.put("list", list);

		return "list";
	}

	/**
	 * 获取单个页面
	 * 
	 * @PathVariable("seckillId") 这个尼玛好像一定要加
	 * 
	 * @param map
	 * @param seckillId
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(Map<String, Object> map, 
			@PathVariable("seckillId") Long seckillId) {

		if (seckillId == null) {
			return "redirect:/list";
		}
		// 获取单个页面
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "redirect:/list";
			// return "forward:/seckill/list";
		}
		map.put("seckill", seckill);

		return "detail";
	}

	/**
	 * ajax接口 返回json
	 * 
	 * 自己想说下对ResponseBody这个注解的理解
	 * 
	 * 官方解释：响应的内容应该被直接写回到HTTP响应体（即response的body中）中去
	 * 
	 * 自己理解：就是局部返回数据，方便数据的传送 直接写回response的body中，其他的响应头全部不变。
	 * 
	 * 2018年1月14日17:04:54 今天自己进行了测试，返回就是就是json数据
	 * 前端直接用即可，很强
	 * 
	 * 比如最后一个函数（http://localhost:8080/seckill/time/now）
	 * 访问不进行渲染，直接返回json，供前端的人员使用，这样交互的方便很多了
	 * {"success":true,"data":1515921153359,"error":null}
	 * 
	 * @param seckillId
	 * @return
	 */

	@RequestMapping(value = "/{seckillId}/exposer", 
			//method = RequestMethod.POST, 
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(
			@PathVariable("seckillId") Long seckillId) {

		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", 
			//method = RequestMethod.POST, 
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
			@PathVariable("seckillId") Long seckillId, 
			@PathVariable("md5") String md5,
			@CookieValue(value = "killPhone", required = false) Long phone) {
		
		// springmvc valid 以后学一下 spring的验证信息
		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}

		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckillProduce(seckillId, phone, md5);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}
		return result;
	}
	
	/**
	 * 我日， prproduces = {"application/json;charest=UTF-8"})
	 * charest=UTF-8  	等号中间不能有空格，有空格就报 http500错误
	 * 真是日了狗了，我曹
	 * 
	 * @return
	 */
	@RequestMapping(value = "/time/now", method = RequestMethod.GET,
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}











