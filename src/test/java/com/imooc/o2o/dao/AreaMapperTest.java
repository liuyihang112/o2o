package com.imooc.o2o.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaMapperTest extends BaseTest {
	@Autowired
	private AreaMapper areaMapper;
	
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaMapper.queryArea();
		for(Area area : areaList) {
			System.out.println(area);
		}
	}
	@Test
	public void testInsert() {
		Area area = new Area();
		area.setAreaName("广州");
		area.setPriority(5);
		int res = areaMapper.insert(area);
		System.out.println(res);
	}
}
