package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.IContentService;

@Service
public class ContentServiceImpl implements IContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC}")
	private String REST_CONTENT_SYNC;

	@Override
	public EUDataGridResult getContentList(long catId, Integer page,
			Integer rows) {
		// 根据category_id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(catId);
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		// 取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(pageInfo.getList());
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult saveContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		// 添加缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC
					+ content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult editContent(TbContent content) {
		contentMapper.updateByPrimaryKey(content);
		// 添加缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC
					+ content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(long ids) {
		TbContent content = contentMapper.selectByPrimaryKey(ids);
		contentMapper.deleteByPrimaryKey(ids);
		// 添加缓存同步
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

}
