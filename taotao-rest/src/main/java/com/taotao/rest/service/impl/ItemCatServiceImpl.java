package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.ItemCat;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.IItemCatService;

@Service
public class ItemCatServiceImpl implements IItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCatList() {
		ItemCatResult result = new ItemCatResult();
		List<?> list = getItemCatListByParentId(0);
		result.setData(list);
		return result;
	}
	
	private List<?> getItemCatListByParentId(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list1 = itemCatMapper.selectByExample(example);
		
		List result = new ArrayList();
		int count = 0;
		for(TbItemCat itemCat : list1) {
			if(itemCat.getIsParent()) {
				ItemCat item1 = new ItemCat();
				item1.setUrl("/products/" + itemCat.getId() +".html");
				item1.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
				item1.setItems(getItemCatListByParentId(itemCat.getId()));
				result.add(item1);
				count ++;
				if(itemCat.getParentId() == 0 && count >= 14) {
					break;
				}
			} else {
				result.add("/products/"+ itemCat.getId() +".html|" + itemCat.getName());
			}
		}
		return result;
	}

}
