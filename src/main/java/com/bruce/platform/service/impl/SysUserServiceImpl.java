package com.bruce.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruce.platform.dao.SysUserMapper;
import com.bruce.platform.model.SysUser;
import com.bruce.platform.service.ISysUserService;

@Controller
@RequestMapping("/user")
public class SysUserServiceImpl implements ISysUserService {
    
	/**
     * 使用@Autowired注解标注userMapper变量，
     * 当需要使用UserMapper时，Spring就会自动注入UserMapper
     */
    @Resource 
    private SysUserMapper sysUserMapper;//注入dao
	
    @ResponseBody
    @RequestMapping(value="/user")
    public List<SysUser> getAll(){
    	return sysUserMapper.getAll();
    }
    
    @ResponseBody
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public SysUser get(@PathVariable("id") String id){
    	return sysUserMapper.selectByPrimaryKey(id);
    }
     
    @RequestMapping(value="/user/{id}",method=RequestMethod.POST)
    public String post(@PathVariable("id") Integer id){
        System.out.println("post"+id);
        return "/hello";
    }
     
    @RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
    public String put(@PathVariable("id") Integer id){
        System.out.println("put"+id);
        return "/hello";
    }
     
    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        System.out.println("delete"+id);
        return "/hello";
    }
}
