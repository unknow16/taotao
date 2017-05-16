package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.rest.service.IItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private IItemService itemService;
	
	@RequestMapping("/base/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBase(@PathVariable Long itemId) {
		return itemService.getItemBaseInfo(itemId);
	}
	
	@RequestMapping("/desc/{itemId}") 
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		return itemService.getItemDescInfo(itemId);
	}
	
	@RequestMapping("/param/{itemId}") 
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable Long itemId) {
		return itemService.getItemParamInfo(itemId);
	}
}
