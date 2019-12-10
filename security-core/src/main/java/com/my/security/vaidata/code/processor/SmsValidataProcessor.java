package com.my.security.vaidata.code.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.CodeContant;
import com.my.security.vaidata.code.SmsSender;
import com.my.security.vaidata.code.ValidataCode;

@Component("smsValidataProcessor")
public class SmsValidataProcessor extends AbstractValidataProcessor<ValidataCode> {


	@Autowired
	private SmsSender smsSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidataCode code) throws ServletRequestBindingException {
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), CodeContant.MOBILE);
		smsSender.send(mobile, code.getCode());
	}

}
