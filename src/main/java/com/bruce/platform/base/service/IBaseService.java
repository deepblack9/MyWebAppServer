package com.bruce.platform.base.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<T, ID extends Serializable> {
	
	public static final String BEAN_ID = "baseService";
	
	int deleteByPrimaryKey(ID id);
	int insert(T record);
	int insertSelective(T record);
	T selectByPrimaryKey(ID id);
	int updateByPrimaryKeySelective(T record);
	int updateByPrimaryKeyWithBLOBs(T record);
	int updateByPrimaryKey(T record);
	List<T> getAll();
}
