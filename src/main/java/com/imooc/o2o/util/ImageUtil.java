package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	
	//要使用的水印照片的绝对路径
	private static String basePath = "G:/java大业视频/SSM到Spring Boot-从零开发校园商铺平台 加/网站用到的图片集_wosn.net/images/item/shop/15/2017060522042982266.png";
	//时间format对象
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = (Logger)LoggerFactory.getLogger(ImageUtil.class);
	//加上水印的方法
	/**
	 * 处理缩略图，返回新生成图片的相对值路径
	 */
	public static String generateThumbnail(File thumbnail, String targetAddr) {
		//随即名，年月日时分秒加五位随机数
		String realFileName = getRandomFileName();
		//扩展名，文件的拓展名
		String extension = getFileExtension(thumbnail);
		String relativeAddr = targetAddr + realFileName + extension;
		//目录可能不存在，若不存在，去创建目录
		makeDirPath(targetAddr);
		logger.debug("current relativeAddr is :" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr id :" + PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200,200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath)),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	private static String getFileExtension(File thumbnail) {
		String fileName = thumbnail.getName();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		return suffix;
	}

	/**
	 * 生成随机文件名，当前年月日时分秒 + 5位随机数
	 */
	public static String getRandomFileName() {
		//生成随机文件名，采用年月日时分秒+五位随机数，
		int rannum = (int) (r.nextDouble()*(99999-10000+1) + 10000);
		String timeStr = sDateFormat.format(new Date());
		return timeStr + rannum;
	}
}
