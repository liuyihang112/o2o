package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.Area;

public interface AreaMapper {

	List<Area> queryArea();
	int insert(Area area);
}
