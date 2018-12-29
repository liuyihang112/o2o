package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopExecution {

	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺shul
	private int count;
	//操作的Shop(增删改查时使用的到)
	private Shop shop;
	//shop列表(查询店铺列表的时候使用)
	private List<Shop> shopList;
	
	public ShopExecution() {
		
	}
	//店铺操作失败的时候使用的构造器，不返回shop对象
	public ShopExecution(ShopStateEnum shopStateEnum) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
	}
	
	//店铺操作成功的时候使用的构造器
	public ShopExecution(ShopStateEnum shopStateEnum, Shop shop) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shop = shop;
	}
	
	//店铺操作成功的时候使用的构造器,针对shopList
	public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shops) {
		this.state = shopStateEnum.getState();
		this.stateInfo = shopStateEnum.getStateInfo();
		this.shopList = shops;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
	
}
