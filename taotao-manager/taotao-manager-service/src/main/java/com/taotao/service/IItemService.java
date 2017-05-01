package com.taotao.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItem;

public interface IItemService {

	TbItem getItemById(long itemId);
	
	EUDataGridResult getItemList(int page, int rows);

	TaotaoResult createItem(TbItem item, String desc, String itemParams)  throws Exception;
}
