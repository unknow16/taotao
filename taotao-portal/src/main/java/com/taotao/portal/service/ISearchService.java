package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

public interface ISearchService {

	SearchResult search(String queryString, Long page);
}
