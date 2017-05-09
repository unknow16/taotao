package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.rest.service.IRedisService;

@Controller
@RequestMapping("/cache/sync")
public class RedisController {

	@Autowired
	private IRedisService redisService;
	
	@RequestMapping("/content/{contentCid}")
	public TaotaoResult contentCacheSync(@PathVariable Long contentCid) {
		TaotaoResult result = redisService.syncContent(contentCid);
		return result;
	}

}
