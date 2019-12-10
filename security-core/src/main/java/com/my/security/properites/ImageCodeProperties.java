package com.my.security.properites;

import lombok.Getter;
import lombok.Setter;

public class ImageCodeProperties  extends SmsCodeProperties{

	@Setter
	@Getter
	private int width = 67;
	@Setter
	@Getter
	private int height = 23;
	//一般图片验证码是4位，短信一般是6位，为了默认设置，处理如下
	public  ImageCodeProperties() {
		this.setLength(4);
	}

}
