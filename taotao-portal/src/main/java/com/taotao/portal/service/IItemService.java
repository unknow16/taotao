package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;

public interface IItemService {

	ItemInfo getItemBaseInfo(Long itemId);
	TbItemDesc getItemDescInfo(Long itemId);
	String getItemParamInfo(Long itemId);
}
