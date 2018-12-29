package com.imooc.o2o.service.impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exception.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Resource
	private ShopMapper shopMapper;

	@Override
	@Transactional          
	//addShop为什么用File做参数，因为这样方便进行单元测试，可以直接new File出来。我日，我就说为啥要用File。原来是这样
	public ShopExecution addShop(Shop shop, File shopImg) {
		/**
		 * 1.先进行空值判断，按照数据库的非空去做空值判断
		 * 2.insert shop
		 * 3.生成图片文件并保存起来
		 * 4.update shop
		 * 
		 */
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			if (shop.getOwner().getUserId() == null) {
				return new ShopExecution(ShopStateEnum.NULL_USER);
			}
			if (shop.getShopName() == null) {
				return new ShopExecution(ShopStateEnum.NULL_SHOPNAME);
			}else {
				try {
					shop.setEnableStatus(0);//审核中
					shop.setCreateTime(new Date());
					shop.setLastEditTime(new Date());
					int res = shopMapper.insertShop(shop);
					if (res <= 0) {
						throw new ShopOperationException("店铺创建失败");
					}
					if (shopImg != null) {
						try {
							addShopImg(shop,shopImg);
						} catch (Exception e) {
							throw new ShopOperationException("add shopImg error" + e.getMessage());
						}
					}
					res = shopMapper.updateShop(shop);
					if (res <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				} catch (Exception e) {
					throw new ShopOperationException("add Shop errer" + e.getMessage());
				}
			}
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	
	//这个File，除了后面加水印用到和获取拓展名用到外，其他所有的路径都是使用的PathUtil或者ImageUtil中定义的地址
	public static void addShopImg(Shop shop, File shopImg) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		shop.setShopImg(shopImgAddr);
	}
	

}
