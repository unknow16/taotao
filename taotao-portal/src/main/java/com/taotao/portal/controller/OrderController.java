package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.ICartService;
import com.taotao.portal.service.IOrderService;

@Controller
public class OrderController {
	
	@Autowired
	private ICartService cartService;

	@Autowired
	private IOrderService orderService;
	
	@RequestMapping("/order/order-cart")
	public String showOrderDetail(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.getCartItemList(request);
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(Order order, Model model) {
		String orderId = orderService.createOrder(order);
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", order.getPayment());
		model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}
}
