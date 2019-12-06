package com.my.security.vaidata.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageCode {

	private String code;
	private BufferedImage image;
	private LocalDateTime localDateTime;

	public ImageCode(String code, BufferedImage image, int expireSecode) {
		super();
		this.code = code;
		this.image = image;
		this.localDateTime = LocalDateTime.now().plusSeconds(expireSecode);
	}

	public boolean isExpire() {
		return LocalDateTime.now().isAfter(this.localDateTime);
	}

}
