package com.imooc.o2o.util;

public class PathUtil {
	
	private static String seperator = System.getProperty("file.separator");
	
	/**
	 * 设置生成图片的存放根目录
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "G:/java大业视频/SSM到Spring Boot-从零开发校园商铺平台 加/Imgae";
		}else {
			basePath = "/home/xiangze/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	/**
	 * 设置生成图片的存放子目录
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
}
