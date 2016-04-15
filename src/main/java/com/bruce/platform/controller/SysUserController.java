package com.bruce.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruce.platform.base.mapper.BaseMapper;
import com.bruce.platform.base.service.impl.BaseServiceImpl;
import com.bruce.platform.model.SysUser;

@Controller
@RequestMapping("/user")
public class SysUserController {
    
//	@Resource 
//    private SysUserServiceImpl SysUserServiceImpl;
	@Resource 
    private BaseServiceImpl<SysUser, String> baseServiceImpl;
	
    @ResponseBody
    @RequestMapping(value="/user")
    public List<SysUser> getAll() {
    	return baseServiceImpl.getAll();
    }
    
    @ResponseBody
    @RequestMapping(value="/user/{id}",method=RequestMethod.GET)
    public SysUser get(@PathVariable("id") String id){
    	return baseServiceImpl.selectByPrimaryKey(id);
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
    
    //service内异常处理
//    @ExceptionHandler
//    public ModelAndView exceptionHandler(Exception ex){
//        ModelAndView mv = new ModelAndView("error");
//        mv.addObject("exception", ex);
//        System.out.println("in testExceptionHandler");
//        return mv;
//    }
    
    @RequestMapping("/error")
    public String error() {
//        int i = 5/0;
    	throw new IllegalArgumentException("不好意思,参数错了");
//        return "hello";
    }
}
