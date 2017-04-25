package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface IItemCatService {
	
	List<EUTreeNode> getItemCatList(long parentId);
}
