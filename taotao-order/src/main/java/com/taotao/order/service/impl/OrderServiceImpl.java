package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.IOrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	
	@Value("${ORDER_ID_INIT_VALUE}")
	private Integer ORDER_ID_INIT_VALUE;
	
	@Value("${ORDER_ITEM_ID_KEY}")
	private String ORDER_ITEM_ID_KEY;
	
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList,
			TbOrderShipping shipping) {
		//redis生成order id
		String string = jedisClient.get(ORDER_ID_KEY);
		if(StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_ID_KEY, ORDER_ID_INIT_VALUE + "");
		}
		long orderId = jedisClient.incr(ORDER_ID_KEY);
		order.setOrderId(orderId + "");
		//'状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		// 0 未评价 1评价
		order.setBuyerRate(0);
		orderMapper.insert(order);
		
		for (TbOrderItem tbOrderItem : itemList) {
			tbOrderItem.setId(jedisClient.incr(ORDER_ITEM_ID_KEY) + "");
			tbOrderItem.setOrderId(orderId + "");
			orderItemMapper.insert(tbOrderItem);
		}
		
		shipping.setOrderId(orderId + "");
		shipping.setCreated(date);
		shipping.setUpdated(date);
		orderShippingMapper.insert(shipping);
		return TaotaoResult.ok(orderId);
	}

}
