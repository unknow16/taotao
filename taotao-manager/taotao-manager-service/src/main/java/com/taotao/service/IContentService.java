package com.taotao.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;

public interface IContentService {

	EUDataGridResult getContentList(long catId, Integer page, Integer rows);
	TaotaoResult saveContent(TbContent content);
	TaotaoResult editContent(TbContent content);
	TaotaoResult deleteContent(long ids);
}
