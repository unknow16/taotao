package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	
	@Value("${SSO_TOKEN_PATH}")
	public String SSO_TOKEN_PATH;
	
	@Value("${SSO_LOGIN_PATH}")
	public String SSO_LOGIN_PATH;
	
	@Value("${SSO_LOGOUT_PATH}")
	private String SSO_LOGOUT_PATH;

	@Override
	public TbUser getUserByToken(String token) {
		try {
			String userJson = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_PATH + token);
			TaotaoResult result = TaotaoResult.formatToPojo(userJson, TbUser.class);
			TbUser user = null;
			if(null != result) {
				user = (TbUser)result.getData();
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean logout(String token, HttpServletRequest request, HttpServletResponse response) {
		try {
			CookieUtils.deleteCookie(request, response, "TT_TOKEN");
			String resultJson = HttpClientUtil.doGet(SSO_BASE_URL + SSO_LOGOUT_PATH + token);
			TaotaoResult result = TaotaoResult.format(resultJson);
			if(result.getStatus() == 200) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}
