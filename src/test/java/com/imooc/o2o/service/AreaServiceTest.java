package com.imooc.o2o.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {

	@Resource
	private AreaService areaService;

	@Test
	public void testSelectAllArea() {
		List<Area> list = areaService.selectAllArea();
		for(Area area : list) {
			System.out.println(area);
		}
	}
	
	@Test
	public void testInsertArea() {
		Area area = new Area();
		area.setAreaName("天津");
		area.setPriority(3);
		areaService.insertArea(area);
	}

}
