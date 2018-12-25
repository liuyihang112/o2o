package com.imooc.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;

import ch.qos.logback.classic.*;

@Controller
@RequestMapping("/superadmin")
public class AreaController { 
	@Resource
	private AreaService areaService;
	Logger logger = (Logger) LoggerFactory.getLogger(getClass());
	@RequestMapping(value="/listarea",method=RequestMethod.GET)
	//表示将返回的modelmap转化成json返回给前段
	@ResponseBody
	private Map<String, Object> listArea(){
		logger.info("listArea开始了");
		Map<String, Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.selectAllArea();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errmsg", e.toString());
		}
		logger.info("listArea结束了");
		logger.debug("debug");
		logger.error("error");
		return modelMap;
	}
	
	
	@RequestMapping(value="/insertarea" ,method=RequestMethod.POST)
	@ResponseBody
	private void insert(HttpServletRequest request) {
		logger.info("方法开始");
		Area area = new Area();
		area.setAreaName(request.getParameter("areaId"));
		area.setPriority(Integer.valueOf(request.getParameter("priority")));
		areaService.insertArea(area);
		logger.info("方法介绍");
	}
	
}
