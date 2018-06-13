package com.xkcoding.lxdemo.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.xkcoding.lxdemo.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 上传
 * </p>
 *
 * @package: com.xkcoding.lxdemo.controller
 * @description： 上传
 * @author: yangkai.shen
 * @date: Created in 2018/6/13 下午3:57
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadController {
	private static ConcurrentMap<String, Integer> cache1 = Maps.newConcurrentMap();
	private static Cache<String, Integer> cache2 = CacheBuilder.newBuilder()
			//设置对象一分钟之后从缓存中清除
			.expireAfterWrite(1, TimeUnit.MINUTES).build();

	private static final Integer MAX_UPLOAD = 8;

	@GetMapping("/1")
	public Map demo1() {
		Map<String, Object> ret = Maps.newHashMap();

		//取当前 session
		String session = RequestUtil.getCurrentRequest().getSession().getId();
		Integer upload = cache1.get(session);

		// 判断缓存里是否已存在
		if (upload != null) {
			if (upload >= MAX_UPLOAD) {
				ret.put("message", "当前上传文件个数已经到达8个！");
			} else {
				cache1.put(session, upload + 1);
			}
		} else {
			cache1.put(session, 1);
		}

		// 当前 session 值
		ret.put("session", session);
		// 当前用户已经上传个数
		ret.put("time", cache1.get(session));
		// 缓存 size
		ret.put("cacheSize", cache1.size());
		return ret;
	}

	@GetMapping("/2")
	public Map demo2() {
		Map<String, Object> ret = Maps.newHashMap();

		//取当前 session
		String session = RequestUtil.getCurrentRequest().getSession().getId();
		Integer upload = cache2.getIfPresent(session);

		// 判断缓存里是否已存在
		if (upload != null) {
			if (upload >= MAX_UPLOAD) {
				ret.put("message", "当前上传文件个数已经到达8个！");
			} else {
				cache2.put(session, upload + 1);
			}
		} else {
			cache2.put(session, 1);
		}

		// 当前 session 值
		ret.put("session", session);
		// 当前用户已经上传个数
		ret.put("time", cache2.getIfPresent(session));
		// 缓存 size
		ret.put("cacheSize", cache2.size());
		return ret;
	}
}
