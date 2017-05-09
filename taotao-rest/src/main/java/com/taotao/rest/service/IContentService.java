package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;

public interface IContentService {

	TaotaoResult getContentListByCatId(long catId);
}
