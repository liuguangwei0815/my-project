package com.my.security.vaidata.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsDefaultSenderImpl implements SmsSender {
	
  @Override
  public void send(String mobile,String code) {
	  log.info("moble:{},发送了验证码:{}",mobile,code);
  }
}
