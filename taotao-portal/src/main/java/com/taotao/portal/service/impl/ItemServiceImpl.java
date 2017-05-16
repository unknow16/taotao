package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;
	@Value("${ITEM_DESC_INFO}")
	private String ITEM_DESC_INFO;
	@Value("${ITEM_PARAM_INFO}")
	private String ITEM_PARAM_INFO;
	
	@Override
	public ItemInfo getItemBaseInfo(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_BASE_INFO + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, ItemInfo.class);
		if(result.getStatus() == 200) {
			ItemInfo item = (ItemInfo)result.getData();
			return item;
		}
		return null;
	}

	@Override
	public TbItemDesc getItemDescInfo(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_INFO + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
		if(result.getStatus() == 200) {
			TbItemDesc desc = (TbItemDesc)result.getData();
			return desc;
		}
		return null;
	}

	@Override
	public String getItemParamInfo(Long itemId) {
		String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_INFO + itemId);
		TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
		if(result.getStatus() == 200) {
			TbItemParamItem param = (TbItemParamItem)result.getData();
			String paramData = param.getParamData();
			//生成html
			// 把规格参数json数据转换成java对象
			List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
			StringBuffer sb = new StringBuffer();
			sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
			sb.append("    <tbody>\n");
			for(Map m1:jsonList) {
				sb.append("        <tr>\n");
				sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
				sb.append("        </tr>\n");
				List<Map> list2 = (List<Map>) m1.get("params");
				for(Map m2:list2) {
					sb.append("        <tr>\n");
					sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
					sb.append("            <td>"+m2.get("v")+"</td>\n");
					sb.append("        </tr>\n");
				}
			}
			sb.append("    </tbody>\n");
			sb.append("</table>");
			//返回html片段
			return sb.toString();

		}
		return null;
	}

}
