/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.examples.showcase.webservice.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.xyxy.platform.examples.showcase.entity.User;
import com.xyxy.platform.examples.showcase.service.AccountEffectiveService;
import com.xyxy.platform.examples.showcase.webservice.rest.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xyxy.platform.modules.core.mapper.BeanMapper;
import com.xyxy.platform.modules.core.web.MediaTypes;

/**
 * cxf在web.xml侦听/cxf, 在applicationContext.xml里侦听/jaxrx，完整访问路径为 /cxf/jaxrs/user/1.xml
 * 
 *
 */
@Path("/user")
public class AccountJaxRsService {

	private static Logger logger = LoggerFactory.getLogger(AccountJaxRsService.class);

	@Autowired
	private AccountEffectiveService accountService;

	@GET
	@Path("/{id}.xml")
	@Produces(MediaTypes.APPLICATION_XML_UTF_8)
	public UserDTO getAsXml(@PathParam("id") Long id) {
		User user = accountService.getUser(id);
		if (user == null) {
			String message = "用户不存在(id:" + id + ")";
			logger.warn(message);
			throw buildException(Status.NOT_FOUND, message);
		}
		return bindDTO(user);
	}

	@GET
	@Path("/{id}.json")
	@Produces(MediaTypes.JSON_UTF_8)
	public UserDTO getAsJson(@PathParam("id") Long id) {
		User user = accountService.getUser(id);
		if (user == null) {
			String message = "用户不存在(id:" + id + ")";
			logger.warn(message);
			throw buildException(Status.NOT_FOUND, message);
		}
		return bindDTO(user);
	}

	private UserDTO bindDTO(User user) {
		UserDTO dto = BeanMapper.map(user, UserDTO.class);
		// 补充Dozer不能自动绑定的属性
		dto.setTeamId(user.getTeam().getId());
		return dto;
	}

	private WebApplicationException buildException(Status status, String message) {
		return new WebApplicationException(Response.status(status).entity(message).type(MediaTypes.TEXT_PLAIN_UTF_8)
				.build());
	}
}
