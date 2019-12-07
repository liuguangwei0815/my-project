/**
 * 
 */
package com.my.security.vaidata.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * @author liuwei
 *  验证码生成器
 */
public interface ImageCodeGennerator {
	ImageCode createImageCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException, NumberFormatException;
}
