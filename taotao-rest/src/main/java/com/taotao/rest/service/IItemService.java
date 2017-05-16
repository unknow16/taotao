package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;

public interface IItemService {

	TaotaoResult getItemBaseInfo(long itemId);
	TaotaoResult getItemDescInfo(long itemId);
	TaotaoResult getItemParamInfo(long itemId);
	
}
