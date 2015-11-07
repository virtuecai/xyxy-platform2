/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.examples.showcase.webservice.soap.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.xyxy.platform.examples.showcase.webservice.soap.WsConstants;
import com.xyxy.platform.examples.showcase.webservice.soap.response.base.WSResult;
import com.xyxy.platform.examples.showcase.webservice.soap.response.dto.UserDTO;

@XmlType(name = "SearchUserResult", namespace = WsConstants.NS)
public class SearchUserResult extends WSResult {

	private List<UserDTO> userList;

	public SearchUserResult() {
	}

	public SearchUserResult(List<UserDTO> userList) {
		this.userList = userList;
	}

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
