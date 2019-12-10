package com.my.security.vaidata.code.processor;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import com.my.security.vaidata.code.ImageCode;

@Component("imageValidataProcessor")
public class ImageValidataProcessor extends AbstractValidataProcessor<ImageCode> {

	

	@Override
	protected void send(ServletWebRequest request, ImageCode code) throws ServletRequestBindingException, IOException {
		// 将图片推送的接口上
		ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
