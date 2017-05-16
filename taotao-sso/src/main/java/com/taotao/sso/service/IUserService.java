package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface IUserService {

	TaotaoResult checkData(String content, Integer type);
	TaotaoResult register(TbUser user);
	TaotaoResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response);
	TaotaoResult getUserInfoByToken(String token);
	TaotaoResult logout(String token);
}
