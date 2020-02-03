/**
 * 
 */
package com.my.security.openfeign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.my.security.bean.AccountInfo;

/**
 * @author liuwei 请求bank2 feign fallback 这个必须在配置文件开启
 * 
 * 
 */
@FeignClient(value = "spring-heima-tcc-bank2", fallback = Bank2AccountClientFallback.class)
public interface Bank2AccountClient {

	@PostMapping("/bank2/updateAccount")
	@Hmily//必须添加这个 否则事务XID 等信息无法传给他下游的服务，导致无法执行comfirm 或者 cancel 方法 .,而且返回值只能是boolan值 true 代表服务调用成功，flase 失败
	public boolean updateAccount(AccountInfo acc);

}
