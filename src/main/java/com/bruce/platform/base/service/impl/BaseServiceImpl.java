package com.bruce.platform.base.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bruce.platform.base.mapper.BaseMapper;
import com.bruce.platform.base.service.IBaseService;

public class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
//	@ResponseBody
//	@ExceptionHandler
//    public ClassExceptionModel exp(HttpServletRequest request, Exception ex) {
//
//        // 记录日志
//        logger.error(ex.getMessage(), ex);
//        ClassExceptionModel classExceptionModel = new ClassExceptionModel();
//        // 根据不同错误转向不同页面 或者 返回不同的JSON信息
//        if (ex instanceof Exception) {
////            resultViewName = "error";
//        } else {
//            // 异常转换
//            ex = new Exception("系统太累了，需要休息!");
//        }
//        request.setAttribute("ex", ex);
//
//        String xRequestedWith = request.getHeader("X-Requested-With");
////        if (!StringUtils.isEmpty(xRequestedWith)) {
////            // ajax请求
////            resultViewName = "errors/ajax-error";
////
////        }
//        ModelAndView modelAndView = new ModelAndView("error");//error页面
//        modelAndView.addObject("errorMessage",ex.getMessage());
//        return modelAndView;
//
////        return resultViewName;
//    }
	@Resource
	private BaseMapper<T, ID> baseMapper;
	
	public int deleteByPrimaryKey(ID id) {
		return baseMapper.deleteByPrimaryKey(id);
	}
	
	public int insertSelective(T record) {
		return baseMapper.insertSelective(record);
	}
	
	public T selectByPrimaryKey(ID id) {
		return baseMapper.selectByPrimaryKey(id);
	}
	
	public int updateByPrimaryKeySelective(T record) {
		return baseMapper.updateByPrimaryKey(record);
	}
	
	public int updateByPrimaryKeyWithBLOBs(T record) {
		return baseMapper.updateByPrimaryKeyWithBLOBs(record);
	}
	
	public int updateByPrimaryKey(T record) {
		return baseMapper.updateByPrimaryKey(record);
	}
	
	public int insert(T record) {
		return baseMapper.insert(record);
	}

	public List<T> getAll() {
		return baseMapper.getAll();
	}
}
