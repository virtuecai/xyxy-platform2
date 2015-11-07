/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.modules.core.security.utils;

import java.io.IOException;

import com.xyxy.platform.modules.core.security.utils.Digests;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.xyxy.platform.modules.core.utils.Encodes;

public class DigestsTest {

	@Test
	public void digestString() {
		String input = "user";
		byte[] sha1Result = Digests.sha1(input.getBytes());
		System.out.println("sha1 in hex result                               :" + Encodes.encodeHex(sha1Result));

		byte[] salt = Digests.generateSalt(8);
		System.out.println("salt in hex                                      :" + Encodes.encodeHex(salt));
		sha1Result = Digests.sha1(input.getBytes(), salt);
		System.out.println("sha1 in hex result with salt                     :" + Encodes.encodeHex(sha1Result));

		sha1Result = Digests.sha1(input.getBytes(), salt, 1024);
		System.out.println("sha1 in hex result with salt and 1024 interations:" + Encodes.encodeHex(sha1Result));

	}

	@Test
	public void digestFile() throws IOException {
		Resource resource = new ClassPathResource("/logback.xml");
		byte[] md5result = Digests.md5(resource.getInputStream());
		byte[] sha1result = Digests.sha1(resource.getInputStream());
		System.out.println("md5: " + Encodes.encodeHex(md5result));
		System.out.println("sha1:" + Encodes.encodeHex(sha1result));
	}
}
