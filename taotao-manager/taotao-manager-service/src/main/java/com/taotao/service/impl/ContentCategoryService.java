package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.IContentCategoryService;

@Service
public class ContentCategoryService implements IContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getContentCategoryByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		List<EUTreeNode> result = new ArrayList<EUTreeNode>();
		for(TbContentCategory c : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(c.getId());
			node.setText(c.getName());
			node.setState(c.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory cat = new TbContentCategory();
		cat.setIsParent(false);
		cat.setName(name);
		cat.setParentId(parentId);
		cat.setSortOrder(1);
		cat.setStatus(1);
		cat.setCreated(new Date());
		cat.setUpdated(new Date());
		//插入
		contentCategoryMapper.insert(cat);
		//如果其父节点为子节点，更新为父
		TbContentCategory catParent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!catParent.getIsParent()) {
			catParent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(catParent);
		}
		return TaotaoResult.ok(cat);
	}

	@Override
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {
		//删除此节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		if(parentId != null) {
			//判断并更新父节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
			if(list == null || list.size() <= 0) {
				TbContentCategory catParent = contentCategoryMapper.selectByPrimaryKey(parentId);
				catParent.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(catParent);
				return TaotaoResult.ok();
			}
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		return TaotaoResult.ok();
	}

}
