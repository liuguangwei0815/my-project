/**
 * 
 */
package com.my.security.vaidata.code;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author liuwei
 *  验证码生成器
 */
public interface ValidataCodeGennerator {

	ValidataCode createImageCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException, NumberFormatException ;
}
