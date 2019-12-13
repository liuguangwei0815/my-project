package com.my.security.vaidata.code.enums;

import com.my.security.vaidata.code.SecurityContant;

public enum CodeValidataType {

	IMAGE {
		@Override
		public String getRequestParamName() {
			return SecurityContant.IMAGECODE;
		}
	},
	SMS

	{
		@Override
		public String getRequestParamName() {
			return SecurityContant.SMSCODE;
		}
	};

	/**
	 * 获取表单提交过来的Name 名称
	 * 
	 * @return
	 */
	public abstract String getRequestParamName();

}
