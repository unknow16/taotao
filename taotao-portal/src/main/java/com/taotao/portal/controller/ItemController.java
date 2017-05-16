package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.IItemService;

@Controller
public class ItemController {
	
	@Autowired
	private IItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ItemInfo item = itemService.getItemBaseInfo(itemId);
		model.addAttribute("item", item);
		return "item";
	}
	
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String showDesc(@PathVariable Long itemId) {
		TbItemDesc desc = itemService.getItemDescInfo(itemId);
		return desc.getItemDesc();
	}
	
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String showParam(@PathVariable Long itemId) {
		String param = itemService.getItemParamInfo(itemId);
		return param;
	}

}
