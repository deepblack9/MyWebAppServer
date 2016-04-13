package com.bruce.framework.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bruce.framework.base.dao.IBaseDAO;
import com.bruce.platform.model.SysUser;

/**
 * 框架基础Service类接口(基于Spring业务逻辑层),所有模块的业务逻辑接口均继承该接口。<br>
 * <p/> 目前实现的功能有（根据实际需要可扩充）<br>
 * <p/> 1）取得基础Dao的接口<br>
 * 2) 调用基础Dao的全部方法
 * 
 * @author Aladding
 */
public interface IBaseService {

	public static final String BEAN_ID = "baseService";

	public static Logger log = LoggerFactory.getLogger(IBaseService.class);
	
	public SysUser get();

    public String get(Integer id);

    public String post(Integer id);

    public String put(Integer id);
    
    public String delete(Integer id);
}
