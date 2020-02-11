/**
 * 
 */
package com.my.security.ioc.getbean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuwei
 *
 */
@Data
public class BeanAwareCode2 {

	private String id;
	private String name;

	public BeanAwareCode2() {
	}

	public BeanAwareCode2(String name) {
		this.name = name;
	}

}
