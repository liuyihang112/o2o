package com.imooc.o2o.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.Test;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	
	@Resource
	private ShopService shopService;
	
	@Test
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试店铺3");
		shop.setShopDesc("测试3");
		shop.setShopAddr("测试3");
		shop.setPhone("test1");
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("G:/java大业视频/SSM到Spring Boot-从零开发校园商铺平台 加/网站用到的图片集_wosn.net/images/item/shop/15/2017060522042982266.png");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop, is,shopImg.getName());
		System.out.println(se.getState());
		
	}
}
