package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.IItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private IItemCatService service;
	
	@RequestMapping("/item/cat/list")
	public @ResponseBody List<EUTreeNode> getItemCatList(@RequestParam(value="id", defaultValue="0")Integer parentId) {
		List<EUTreeNode> list = service.getItemCatList(parentId);
		return list;
	}
}
