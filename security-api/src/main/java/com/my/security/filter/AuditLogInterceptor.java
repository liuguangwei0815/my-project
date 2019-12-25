package com.my.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.my.security.audit.AuditLog;
import com.my.security.audit.AuditLogResposity;
import com.my.security.user.entity.User;

import lombok.extern.slf4j.Slf4j;

/**
 * 审计
 * 
 * @author liuwei
 *
 */
@Component
@Slf4j
public class AuditLogInterceptor implements HandlerInterceptor {

	@Autowired
	private AuditLogResposity auditLogResposity;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("开始方法开始记录审计。。。");
		Object user = request.getAttribute("user");
		AuditLog log = new AuditLog();
		log.setMethod(request.getMethod());
		log.setPath(request.getRequestURI());
		//log.setUserName(user != null ? ((User) user).getUserName() : ""); 这里不写会直接通过实体类的注解进行查找bean 取得里面的AuditorAware<T> getCurrentAuditor 
		auditLogResposity.save(log);
		request.setAttribute("auditLogId", log.getId());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("结束方法开始记录审计。。。");
		Long auditLogId = (Long) request.getAttribute("auditLogId");
		AuditLog log = auditLogResposity.findById(auditLogId).orElse(null);
		if (log != null) {
			log.setStatus(response.getStatus());
			auditLogResposity.save(log);
		}
	}

}
