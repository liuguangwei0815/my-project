package com.my.security.asyn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.Data;

@Data
@Component
public class DefrerResultHolder {

	private Map<String, DeferredResult<String>> map = new HashMap<>();

}
