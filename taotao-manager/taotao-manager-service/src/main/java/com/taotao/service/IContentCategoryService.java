package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface IContentCategoryService {

	List<EUTreeNode> getContentCategoryByParentId(long parentId);
	TaotaoResult insertContentCategory(long parentId, String name);
	TaotaoResult deleteContentCategory(Long parentId, Long id);
	TaotaoResult updateContentCategory(Long id, String name);
}
