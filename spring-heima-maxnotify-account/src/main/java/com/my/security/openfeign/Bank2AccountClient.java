/**
 * 
 */
package com.my.security.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.my.security.bean.AccountInfo;

/**
 * @author liuwei 请求bank2 feign fallback 这个必须在配置文件开启
 * 
 * 
 */
@FeignClient(value = "spring-heima-tcc-bank2", fallback = Bank2AccountClientFallback.class)
public interface Bank2AccountClient {

	@PostMapping("/bank2/updateAccount")
	public boolean updateAccount(AccountInfo acc);

}
