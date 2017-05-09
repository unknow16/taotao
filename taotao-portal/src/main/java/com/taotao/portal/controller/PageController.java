package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.service.IContentService;

@Controller
public class PageController {
	
	@Autowired
	private IContentService contentService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("ad1", contentService.getContentList());
		return "index";
	}
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult testPost() {
		return TaotaoResult.ok();
	}
	
	@RequestMapping(value="/postwithparam", method=RequestMethod.POST)
	@ResponseBody
	public String testPostWithParam(String username, String password) {
		return "username:" + username + ", password:" + password;
	}
}
