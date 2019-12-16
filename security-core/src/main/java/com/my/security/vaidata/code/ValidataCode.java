package com.my.security.vaidata.code;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class ValidataCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3136328531296739565L;
	private String code;
	private LocalDateTime localDateTime;

	public ValidataCode(String code, int expireSecode) {
		super();
		this.code = code;
		this.localDateTime = LocalDateTime.now().plusSeconds(expireSecode);
	}
	
	public ValidataCode(String code, LocalDateTime localDateTime) {
		super();
		this.code = code;
		this.localDateTime = localDateTime;
	}
	

	public boolean isExpire() {
		return LocalDateTime.now().isAfter(this.localDateTime);
	}
	
}
