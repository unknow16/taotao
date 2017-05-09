package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;

public interface IRedisService {

	TaotaoResult syncContent(long catId);
}
