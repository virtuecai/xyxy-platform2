/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.modules.core.utils;

import com.xyxy.platform.modules.core.utils.Identities;
import org.junit.Test;

public class IdentitiesTest {

	@Test
	public void demo() {
		System.out.println("uuid: " + Identities.uuid());
		System.out.println("uuid2:" + Identities.uuid2());
		System.out.println("randomLong:  " + Identities.randomLong());
		System.out.println("randomBase62:" + Identities.randomBase62(7));
	}
}
