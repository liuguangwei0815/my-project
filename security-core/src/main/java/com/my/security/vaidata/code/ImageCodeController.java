package com.my.security.vaidata.code;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.processor.ValidataProcessorHolder;

@RestController
public class ImageCodeController {
	
	@Autowired
	private ValidataProcessorHolder validataProcessorHolder;
	
	@GetMapping("/mycode/{type}")
	public void code(HttpServletRequest request, HttpServletResponse response,@PathVariable String type)
			throws IOException, ServletRequestBindingException, NumberFormatException {
		validataProcessorHolder.findValidataProcessorByType(type).create(new ServletWebRequest(request, response));
	}
//
//	private SessionStrategy stategy = new HttpSessionSessionStrategy();
//
//	@Autowired
//	private ValidataCodeGennerator imageCodeGennerator;
//	@Autowired
//	private ValidataCodeGennerator smsCodeGennerator;
//	
//	@Autowired
//	private SmsSender smsSender;
//
//	@GetMapping("/code/image/")
//	public void code(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletRequestBindingException, NumberFormatException {
//		// 生产Image
//		ImageCode code = (ImageCode)imageCodeGennerator.createImageCode(new ServletWebRequest(request));
//		// 将code放到session
//		stategy.setAttribute(new ServletWebRequest(request), CodeContant.SESSIONKEY, code);
//		// 将图片推送的接口上
//		ImageIO.write(code.getImage(), "JPEG", response.getOutputStream());
//
//	}
//	
//	@GetMapping("/code/sms/")
//	public void smscode(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletRequestBindingException, NumberFormatException {
//		// 生产Image
//		ValidataCode code = smsCodeGennerator.createImageCode(new ServletWebRequest(request));
//		// 将code放到session
//		stategy.setAttribute(new ServletWebRequest(request), CodeContant.SESSIONKEY, code);
//		// 将图片推送的接口上
//		//ImageIO.write(code.getImage(), "JsPEG", response.getOutputStream());
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
//		smsSender.send(mobile, code.getCode());
//	}
	
}
