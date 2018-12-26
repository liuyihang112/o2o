package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Shop;

public class ShopExecution {

	//结果状态
	private int status;
	//状态标识
	private String statusInfo;
	//店铺shul
	private int count;
	//操作的Shop(增删改查时使用的到)
	private Shop shop;
	//shop列表(查询店铺列表的时候使用)
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	/*public ShopExecution(ShopSateEnum sateEnum) {
		
	}*/
}
