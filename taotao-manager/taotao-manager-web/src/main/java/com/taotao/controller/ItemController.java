package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.IItemService;

@Controller
public class ItemController {

	@Autowired
	private IItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public @ResponseBody TbItem getItemById(@PathVariable long itemId) {
		return itemService.getItemById(itemId);
	}
	
	@RequestMapping("/item/list")
	public @ResponseBody EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
}
