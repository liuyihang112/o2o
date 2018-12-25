package com.imooc.o2o.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.ShopMapper;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

	@Resource
	private ShopMapper shopMapper;
	
	@Override
	public int insert(Shop shop) {
		int res = shopMapper.insertShop(shop);
		return res;
	}

}
