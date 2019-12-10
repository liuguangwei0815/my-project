package com.my.security.vaidata.code;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ValidataCode {

	private String code;
	private LocalDateTime localDateTime;

	public ValidataCode(String code, int expireSecode) {
		super();
		this.code = code;
		this.localDateTime = LocalDateTime.now().plusSeconds(expireSecode);
	}

	public boolean isExpire() {
		return LocalDateTime.now().isAfter(this.localDateTime);
	}
	
}
