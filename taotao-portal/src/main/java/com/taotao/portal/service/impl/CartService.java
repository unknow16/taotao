package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.ICartService;

@Service
public class CartService implements ICartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;

	@Override
	public TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		
		//取cookie中商品列表
		List<CartItem> list = getCartItemList(request);
		CartItem item = null;
		for (CartItem cartItem : list) {
			//存在
			if(cartItem.getId() == itemId) {
				cartItem.setNum(num + cartItem.getNum());
				item = cartItem;
				break;
			}
		}
		if(item == null ) { // 不存在
			String result = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_INFO + itemId);
			if(StringUtils.isNotBlank(result)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, TbItem.class);
				if(taotaoResult.getStatus() == 200) {
					item = new CartItem();
					TbItem tbItem = (TbItem)taotaoResult.getData();
					item.setId(itemId);
					item.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
					item.setNum(num);
					item.setPrice(tbItem.getPrice());
					item.setTitle(tbItem.getTitle());
					list.add(item);
				}
			}
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok();
	}
	
	public List<CartItem> getCartItemList(HttpServletRequest request) {
		String jsonList = CookieUtils.getCookieValue(request, "TT_CART", true);
		List<CartItem> list = null;
		if(StringUtils.isNotBlank(jsonList)) {
			list = JsonUtils.jsonToList(jsonList, CartItem.class);
		}
		if(null == list) {
			list = new ArrayList<CartItem>();
		}
		return list;
	}

	@Override
	public TaotaoResult updateCartItemNum(Long itemId, Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//取cookie中商品列表
		List<CartItem> list = getCartItemList(request);
		for (CartItem cartItem : list) {
			//存在
			if(cartItem.getId() == itemId) {
				cartItem.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		//取cookie中商品列表
		List<CartItem> list = getCartItemList(request);
		for (CartItem cartItem : list) {
			//存在
			if(cartItem.getId() == itemId) {
				list.remove(cartItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);
		return TaotaoResult.ok();
	}

}
