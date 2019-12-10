package com.my.security.properites;

import lombok.Data;

@Data
public class CodeProperties {
	 ImageCodeProperties image = new ImageCodeProperties();
	 SmsCodeProperties smscode = new SmsCodeProperties();
}
