/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.modules.extension.test.mail;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.xyxy.platform.modules.core.test.spring.SpringContextTestCase;

import com.icegreen.greenmail.util.GreenMail;

@ContextConfiguration(locations = { "/applicationContext-mail.xml" })
public class MailServerSimulatorTest extends SpringContextTestCase {

	@Autowired
	private GreenMail greenMail;

	@Test
	public void greenMail() {
		assertThat(greenMail.getSmtp().getPort()).isEqualTo(3025);
	}
}
