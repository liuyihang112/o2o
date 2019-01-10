package com.imooc.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;


public class ShopCategoryMapperTest extends BaseTest {

	@Autowired
	private ShopCategoryMapper shopCategoryMapper;
	
	
	@Test
	public void testQueryShopCategory() {
		ShopCategory shopCategory = new ShopCategory();
		ShopCategory parent = new ShopCategory();
		parent.setShopCategoryId(3l);
		shopCategory.setParent(parent);
		List<ShopCategory> list = shopCategoryMapper.queryShopCategory(shopCategory);
		if (list != null && !list.isEmpty()) {
			for(ShopCategory sc : list) {
				System.out.println(sc);
			}
		}
	}
}
