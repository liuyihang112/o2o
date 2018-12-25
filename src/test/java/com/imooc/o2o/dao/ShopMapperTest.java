package com.imooc.o2o.dao;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopMapperTest extends BaseTest{
	
	@Autowired
	private ShopMapper shopMapper;
	
	@Test
	public void testInsertShop() {
		Shop shop = new Shop();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		PersonInfo personInfo = new PersonInfo();
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1l);
		personInfo.setUserId(1l);
		shop.setOwner(personInfo);
		shop.setShopCategory(shopCategory);
		shop.setArea(area);
		shop.setShopName("测试店铺");
		shop.setShopDesc("测试");
		shop.setShopAddr("测试");
		shop.setPhone("test");
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int res = shopMapper.insertShop(shop);
		System.out.println(res);
	}
	
	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1l);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		
		int res = shopMapper.updateShop(shop);
		System.out.println(res);
	}
	
	@Test
	public void testDeleteShop() {
		int shopId = 1;
		int res = shopMapper.deleteShop(shopId);
		System.out.println(res);
	}
}
