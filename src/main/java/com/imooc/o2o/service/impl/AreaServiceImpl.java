package com.imooc.o2o.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.AreaMapper;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaMapper areaMapper;
	@Override
	public List<Area> selectAllArea() {
		List<Area> list = areaMapper.queryArea();
		return list;
	}
	
	@Override
	public int insertArea(Area area) {
		return areaMapper.insert(area);
	}

}
