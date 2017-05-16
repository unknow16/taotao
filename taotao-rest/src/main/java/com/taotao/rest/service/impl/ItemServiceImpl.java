package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("REDIS_ITEM_KEY")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;
	
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		String baseKey = REDIS_ITEM_KEY + ":" + itemId + ":base";
		
		//先从缓存中取
		try {
			String itemBase = jedisClient.get(baseKey);
			if(StringUtils.isNotBlank(itemBase)) {
				TbItem item = JsonUtils.jsonToPojo(itemBase, TbItem.class);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		//将商品基础信息存入redis
		try {
			jedisClient.set(baseKey, JsonUtils.objectToJson(item));
			//设置过期时间为一天
			jedisClient.expire(baseKey, REDIS_ITEM_EXPIRE);
			return TaotaoResult.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TaotaoResult getItemDescInfo(long itemId) {
		String descKey = REDIS_ITEM_KEY + ":" + itemId + ":desc";
		try {
			String descJson = jedisClient.get(descKey);
			if(StringUtils.isNotBlank(descJson)) {
				TbItemDesc desc = JsonUtils.jsonToPojo(descJson, TbItemDesc.class);
				return TaotaoResult.ok(desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		try {
			jedisClient.set(descKey, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(descKey, REDIS_ITEM_EXPIRE);
			TaotaoResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TaotaoResult getItemParamInfo(long itemId) {
		String paramKey = REDIS_ITEM_KEY + ":" + itemId + ":param";
		try {
			String paramJson = jedisClient.get(paramKey);
			if(StringUtils.isNotBlank(paramJson)) {
				TbItemParamItem param = JsonUtils.jsonToPojo(paramJson, TbItemParamItem.class);
				return TaotaoResult.ok(param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		TbItemParamItem itemParam = null;
		if(list != null && list.size() > 0) {
			itemParam = list.get(0);
		}
		try {
			jedisClient.set(paramKey, JsonUtils.objectToJson(itemParam));
			jedisClient.expire(paramKey, REDIS_ITEM_EXPIRE);
			return TaotaoResult.ok(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
