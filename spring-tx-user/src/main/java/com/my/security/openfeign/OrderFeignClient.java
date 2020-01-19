/**
 * 
 */
package com.my.security.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liuwei
 */
@FeignClient(value = "spring-tx-order",path = "/customer")//value 访问那个服务 ，path 类似reqestMapping 访问前缀
public interface OrderFeignClient {

	@GetMapping("/my/{id}")
	public String getOrderInfoById(@PathVariable(name = "id") Long id) ;// pathVariable 这个name 一定要写  ，这feign client 的规范
	
}
