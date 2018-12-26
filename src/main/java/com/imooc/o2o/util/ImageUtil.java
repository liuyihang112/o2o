package com.imooc.o2o.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	
	//要使用的图片的公共根目录
	private static String basePath = "";
	//时间format对象
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	//加上水印的方法
	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
		//随即名
		String realFileName = getRandomFileName();
		//扩展名
		String extension = getFileExtension(thumbnail);
		//目录可能不存在，若不存在，去创建目录
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + realFileName);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(200,200)
			.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath + "/water.jpg")),0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (Exception e) {
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

	private static String getFileExtension(CommonsMultipartFile thumbnail) {
		String originalFileName = thumbnail.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 生成随机文件名，当前年月日时分秒 + 5位随机数
	 */
	private static String getRandomFileName() {
		//生成随机文件名，采用年月日时分秒+五位随机数，
		int rannum = (int) (r.nextDouble()*(99999-10000+1) + 10000);
		String timeStr = sDateFormat.format(new Date());
		return timeStr + rannum;
	}
}
