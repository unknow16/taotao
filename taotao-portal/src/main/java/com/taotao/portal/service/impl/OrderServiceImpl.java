package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;

	@Value("${ORDER_CREATE_PATH}")
	private String ORDER_CREATE_PATH;
	
	@Override
	public String createOrder(Order order) {
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_PATH, JsonUtils.objectToJson(order));
		TaotaoResult result = TaotaoResult.format(json);
		if(result.getStatus() == 200) {
			Object orderId = result.getData();
			return orderId.toString();
		}
		return "";
	}

	
}
