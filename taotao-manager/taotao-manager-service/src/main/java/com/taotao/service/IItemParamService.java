package com.taotao.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbItemParam;

public interface IItemParamService {

	TaotaoResult getItemParamByCid(long cid);
	EUDataGridResult getItemParamList(Integer page, Integer rows);
	TaotaoResult insertItemParam(TbItemParam itemParam);
}
