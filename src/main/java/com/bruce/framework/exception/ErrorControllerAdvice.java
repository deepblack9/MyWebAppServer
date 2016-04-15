package com.bruce.framework.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorControllerAdvice {
	
	protected static Logger logger = Logger.getLogger(ErrorControllerAdvice.class);  
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView processUnauthenticatedException(Exception exception) { 
		System.out.println("********************************** 异常 ****************************************");
		ModelAndView model = new ModelAndView("error/error");
		model.addObject("message", "****** " + exception.getMessage());
		
		return model;
	}
}
