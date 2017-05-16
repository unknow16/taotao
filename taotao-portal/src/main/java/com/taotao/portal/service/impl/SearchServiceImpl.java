package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.ISearchService;

@Service
public class SearchServiceImpl implements ISearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, Long page) {
		Map<String, String> param = new HashMap();
		param.put("q", queryString);
		param.put("page", page + "");
		try {
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
			if(result.getStatus() == 200) {
				SearchResult searchResult = (SearchResult)result.getData();
				return searchResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
