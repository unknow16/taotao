package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface ICartService {

	TaotaoResult addCartItem(Long itemId, int num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItemList(HttpServletRequest request);
	TaotaoResult updateCartItemNum(Long itemId, Integer num,
			HttpServletRequest request, HttpServletResponse response);
	TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
