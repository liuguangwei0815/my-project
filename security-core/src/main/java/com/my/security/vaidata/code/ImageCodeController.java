package com.my.security.vaidata.code;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ImageCodeController {

	private SessionStrategy stategy = new HttpSessionSessionStrategy();

	@Autowired
	private ImageCodeGennerator imageCodeGennerator;

	@GetMapping("/image/code")
	public void code(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletRequestBindingException, NumberFormatException {
		// 生产Image
		ImageCode code = imageCodeGennerator.createImageCode(request, response);
		// 将code放到session
		stategy.setAttribute(new ServletWebRequest(request), ImageCodeContant.SESSIONKEY, code);
		// 将图片推送的接口上
		ImageIO.write(code.getImage(), "JPEG", response.getOutputStream());

	}
	
}
