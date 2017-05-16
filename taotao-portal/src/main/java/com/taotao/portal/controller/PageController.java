package com.taotao.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.service.IContentService;
import com.taotao.portal.service.IUserService;

@Controller
public class PageController {
	
	@Autowired
	private IContentService contentService;
	
	@Autowired
	private IUserService userService;

	@RequestMapping("/index")
	public String showIndex(Model model) {
		model.addAttribute("ad1", contentService.getContentList());
		return "index";
	}
	
	@RequestMapping("/user/logout/{token}")
	public String logout(@PathVariable String token, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("ad1", contentService.getContentList());
		userService.logout(token, request, response);
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
