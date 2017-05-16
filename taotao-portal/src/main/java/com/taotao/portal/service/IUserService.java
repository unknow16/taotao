package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface IUserService {

	TbUser getUserByToken(String token);
	boolean logout(String token, HttpServletRequest request, HttpServletResponse response);
}
