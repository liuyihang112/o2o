package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.Shop;

public interface ShopMapper {

	/**
	 * 
	* @Title: insertShop 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param shop
	* @param @return  参数说明 
	* @return int    返回类型  : 1成功   -1失败
	* @throws
	 */
	int insertShop(Shop shop);
	
	int updateShop(Shop shop);
	
	int deleteShop(int shopId);
	
	
}
