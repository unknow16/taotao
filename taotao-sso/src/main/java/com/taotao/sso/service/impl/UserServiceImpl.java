package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	
	@Value("${REDIS_USER_SESSION_EXPIRE_TIME}")
	private Integer REDIS_USER_SESSION_EXPIRE_TIME;
	
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1) {
			criteria.andUsernameEqualTo(content);
		}
		if(type == 2) {
			criteria.andPhoneEqualTo(content);
		}
		if(type == 3) {
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size() <= 0) {
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if(null == list || list.size() <= 0) {
			return TaotaoResult.build(400, "用户名错误！");
		}
		TbUser user = list.get(0);
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400, "密码错误！");
		}
		
		//将用户信息保存到redis
		String token = UUID.randomUUID().toString();
		String key = REDIS_USER_SESSION_KEY + ":" + token;
		user.setPassword(null);
		jedisClient.set(key, JsonUtils.objectToJson(user));
		jedisClient.expire(key, REDIS_USER_SESSION_EXPIRE_TIME);
		
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserInfoByToken(String token) {
		String key = REDIS_USER_SESSION_KEY + ":" + token;
		String userInfo = jedisClient.get(key);
		TbUser user = null;
		if(StringUtils.isNotBlank(userInfo)) {
			user = JsonUtils.jsonToPojo(userInfo, TbUser.class);
		}
		return TaotaoResult.ok(user);
	}

	@Override
	public TaotaoResult logout(String token) {
		String key = REDIS_USER_SESSION_KEY + ":" + token;
		jedisClient.del(key);
		return TaotaoResult.ok();
	}

}
