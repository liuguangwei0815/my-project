package com.my.security.vaidata.code.processor;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.CodeContant;
import com.my.security.vaidata.code.ImageCodeException;
import com.my.security.vaidata.code.ValidataCode;
import com.my.security.vaidata.code.ValidataCodeGennerator;

public abstract class AbstractValidataProcessor<C extends ValidataCode> implements ValidataProcessor {
	
	@Autowired
	Map<String,ValidataCodeGennerator> gennerators;

	private SessionStrategy strategy = new HttpSessionSessionStrategy();

	@Override
	public void create(ServletWebRequest request) throws ServletRequestBindingException, IOException {
		C code = gennerator(request);
		save(request, code);
		send(request, code);
	}

	protected abstract void send(ServletWebRequest request, C code) throws ServletRequestBindingException, IOException;

	private void save(ServletWebRequest request, C code) {
		strategy.setAttribute(request, CodeContant.SESSIONKEY, code.getCode());
	}

	@SuppressWarnings("unchecked")
	protected C gennerator(ServletWebRequest request) throws ServletRequestBindingException {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidataProcessor");
		ValidataCodeGennerator gennertor = getGennerTypeByType(type);
		return (C) gennertor.createImageCode(request);
	}
	
	public ValidataCodeGennerator getGennerTypeByType(String type) {
		String  beanName = type.trim().toLowerCase()+ValidataCodeGennerator.class.getSimpleName();
		ValidataCodeGennerator bean = gennerators.get(beanName);
		if(bean==null)
			throw new ImageCodeException("无法找到对应的bean");
		return bean;
	}

}
