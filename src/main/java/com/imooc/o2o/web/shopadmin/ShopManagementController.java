package com.imooc.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody //自动把返回的map转化成Json
	private Map<String, Object> registerShop(HttpServletRequest request){
		//1.接受并转化相应的参数，包括店铺信息以及图片信息
		logger.info("开始了");
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//前端传值的时候value为shop的key要设为shopstr，这样才能捕捉的到
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			//得到的是什么类型，传入的.class就得是什么类型
			shop = mapper.readValue(shopStr, Shop.class);
			
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
			// TODO: handle exception
		}
		//做图片的上传处理
		CommonsMultipartFile shopImg = null;//spring自带的上传图片的类型
		//spring自带的上传图片解析器
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request中是否有上传的文件流，如果有，就对request做转换
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			//将MultipartFile 转化成spring能处理的CommonsMultipartFile类型，然后这个shopImg是前端约定好的图片的key名字。
			shopImg =(CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2.注册店铺
		
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1l);
			shop.setOwner(owner);
			File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			}catch(IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			try {
				inputStreamToFile(shopImg.getInputStream(), shopImgFile);
			}catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
			ShopExecution se = shopService.addShop(shop, shopImgFile);
			if (se.getState() == ShopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		
	}
	
	//CommonsMultipartFile 无法强转成File，因此，要将CommonsMultipartFile.getInputStream 转化成 File。但是如果文件过大的话呢？据说可以使用MultipartFile
	private static void inputStreamToFile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while((bytesRead = ins.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new RuntimeException("调用InputStreamToFile产生异常" + e.getMessage());
		}finally {
			try {
				if (os != null) {
					os.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("inputStreamToFile关闭IO产生异常" + e.getMessage());
			}
		}
	}
}
