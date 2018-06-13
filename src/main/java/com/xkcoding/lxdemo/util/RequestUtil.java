package com.xkcoding.lxdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Request 工具类
 * </p>
 *
 * @package: com.xkcoding.lxdemo.util
 * @description： Request 工具类
 * @author: yangkai.shen
 * @date: Created in 2018/6/13 下午3:54
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class RequestUtil {

	/**
	 * 获取当前线程的 Request
	 *
	 * @return 获取当前线程的 Request
	 */
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}
}
