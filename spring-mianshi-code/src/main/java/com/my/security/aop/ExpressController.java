/**
 * 
 */
package com.my.security.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei 测试 aop
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class ExpressController {

	@GetMapping("/patternaop1")
	@ResponseBody
	public String patternaop1(String name) {
		return "aop1  ： " + name;
	}

	@GetMapping("/patternaop2")
	@ResponseBody
	public String patternaop2(String name) {
		createException();
		return "aop2 ： " + name;
	}
	

	@GetMapping("/anno")
	@ResponseBody
	@LogAnnotation//标注aop
	public String anno(String name) {
		log.info("调用了 name"+name);
		createException();
		return "anotiaon ： " + name;
	}

	private void createException() {
		throw new RuntimeException("人造异常");
	}

}
