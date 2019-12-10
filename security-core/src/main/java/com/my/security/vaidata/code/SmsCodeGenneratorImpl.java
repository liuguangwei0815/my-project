/**
 * 
 */
package com.my.security.vaidata.code;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.properites.SecurityProperties;

import lombok.Data;

/**
 * @author liuwei
 *
 */
@Data
@Component("smsValidataCodeGennerator")
public class SmsCodeGenneratorImpl implements ValidataCodeGennerator {

	@Autowired
	private SecurityProperties securityProperties;
	

	@Override
	public ValidataCode createImageCode(ServletWebRequest servletWebRequest)
			throws ServletRequestBindingException, NumberFormatException {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmscode().getLength());
		int  expireSecode = securityProperties.getCode().getSmscode().getExprireTime();
		return new ValidataCode(code, expireSecode);
	}
	
}
