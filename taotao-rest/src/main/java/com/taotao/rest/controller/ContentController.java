package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.rest.service.IContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private IContentService contentService;
	
	@RequestMapping("/category/{cId}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long cId) {
		TaotaoResult result = null;
		try {
			result = contentService.getContentListByCatId(cId);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, e.getMessage());
		}
		return result;
	}
}
