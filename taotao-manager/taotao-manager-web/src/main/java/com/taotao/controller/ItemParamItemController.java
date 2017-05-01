package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.IItemParamItemService;

@Controller
public class ItemParamItemController {

	@Autowired
	private IItemParamItemService itemParamItemService;
	
	@RequestMapping("/itemparam/{itemId}")
	public String getItemParamItemByItemId(@PathVariable long itemId, Model model) {
		String html = itemParamItemService.getItemParamItemByItemId(itemId);
		model.addAttribute("itemParam", html);
		return "item";
	}
}
