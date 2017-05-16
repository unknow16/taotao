package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ISearchService;

@Controller
public class SearchController {

	@Autowired
	private ISearchService searchService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue="1")Long page, Model model) {
		try {
			queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
			SearchResult result = searchService.search(queryString, page);
			model.addAttribute("query", queryString);
			model.addAttribute("totalPages", result.getPageCount());
			model.addAttribute("itemList", result.getItemList());
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
}
