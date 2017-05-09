package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.IContentService;

@Controller
public class ContentController {
	
	@Autowired
	private IContentService contentService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EUDataGridResult getContentListByCatId(long categoryId, Integer page, Integer rows) {
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content) {
		return contentService.saveContent(content);
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {
		return contentService.editContent(content);
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		String[] conIds = ids.split(",");
		for(String conId : conIds) {
			contentService.deleteContent(Integer.parseInt(conId));
		}
		return TaotaoResult.ok();
	}
}
