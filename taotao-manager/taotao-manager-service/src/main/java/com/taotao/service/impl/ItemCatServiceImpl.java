package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.IItemCatService;

@Service
public class ItemCatServiceImpl implements IItemCatService {

	@Autowired
	private TbItemCatMapper mapper;
	
	@Override
	public List<EUTreeNode> getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = mapper.selectByExample(example);
		
		List<EUTreeNode> result = new ArrayList<EUTreeNode>();
		for(TbItemCat item : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent()? "closed" : "open");
			result.add(node);
		}
		return result;
	}

}
