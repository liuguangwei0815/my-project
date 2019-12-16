package com.my.security.vaidata.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class ImageCode extends ValidataCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4639969912602339802L;
	@Setter
	@Getter
	private BufferedImage image;

	public ImageCode(String code,BufferedImage image, int expireSecode) {
		super(code, expireSecode);
		this.image = image;
	}

	public ImageCode(String code, BufferedImage image,LocalDateTime localDateTime) {
		super(code, localDateTime);
		this.image = image;
	}
	
	


}
