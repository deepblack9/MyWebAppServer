package com.bruce.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bruce.platform.base.service.IBaseService;
import com.bruce.platform.base.service.impl.BaseServiceImpl;
import com.bruce.platform.dao.SysUserMapper;
import com.bruce.platform.model.SysUser;

@Controller
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, String> implements IBaseService<SysUser, String> {
    
	/**
     * 使用@Autowired注解标注sysUserMapper变量，
     * 当需要使用SysUserMapper时，Spring就会自动注入SysUserMapper
     */
	@Autowired
	private SysUserMapper sysUserMapper;
}
