package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem {

	public String[] getImages() {
		String images = super.getImage();
		if( images != null ) {
			return images.split(",");
		}
		return null;
	}
}
