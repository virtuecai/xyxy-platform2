/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.examples.showcase.demos.cache.memcached;

import com.xyxy.platform.examples.showcase.service.AccountService;

/**
 * 统一定义Memcached中存储的各种对象的Key前缀和超时时间.
 * 
 * @see AccountService#getInitializedUser(String)
 * 
 *
 */
public enum MemcachedObjectType {
	USER("user:", 60 * 60 * 1);

	private String prefix;
	private int expiredTime;

	MemcachedObjectType(String prefix, int expiredTime) {
		this.prefix = prefix;
		this.expiredTime = expiredTime;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiredTime() {
		return expiredTime;
	}

}
