package com.bruce.platform.base.exception;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.bruce.platform.base.model.CommonException;
import com.bruce.platform.base.model.ErrorENUM;
import com.bruce.platform.base.model.ExceptionModel;

@ControllerAdvice
public class GlobalExceptionController implements HandlerExceptionResolver {
	
//	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

//	@ExceptionHandler(SQLException.class)  
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
//    public ModelAndView handleSQLException(HttpServletRequest request, SQLException ex) {  
//        handleLog(request, ex);  
//        Map<String, Object> errorMap = new HashMap<String, Object>();  
//        errorMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);  
//        errorMap.put("Requested", request.getRequestURL());  
//        errorMap.put("message", ex.toString());
//        
//        handleLog(request, ex);
//        ExceptionModel exceptionModel = getExceptionModel(HttpStatus.BAD_REQUEST, ex);
//  
//        return new ModelAndView(JSONUtils.VIEW_NAME, JSONStringView.JSON_MODEL_DATA, errorMap);
//    }
	
	@ResponseBody
    public ModelAndView resolveException(HttpServletRequest request,
        HttpServletResponse response, Object handler, Exception ex) {
		Logger logger = LoggerFactory.getLogger(ex.getClass());
//		logger.error(new Date().toLocaleString() + " 异常信息", ex);
		System.out.println(UUID.randomUUID().toString());
		logger.error(UUID.randomUUID().toString());
		
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
		errorMap.put("Requested", request.getRequestURL());
		errorMap.put("message", ex.toString());
		
//	    if (ex instanceof NumberFormatException) {
//	        return new ModelAndView("exception/number");
//	    } else if (ex instanceof NullPointerException) {
//	        return new ModelAndView("exception/null");
//	//    } else if (ex instanceof BusinessException) {
//	//        return new ModelAndView("exception/business");
//	    } else if (ex instanceof SocketTimeoutException || ex instanceof ConnectException) {
//	        try {
//	        response.getWriter().write("网络异常");
//	        } catch (IOException e) {
//	        e.printStackTrace();
//	        }
//	        return new ModelAndView("exception/net_error");
//	    }
	//    else if(ex instanceof AjaxException){
	//       System.out.println("-=-=");
	//    }
	    return new ModelAndView("error", errorMap);
    }
//	
//	private ExceptionModel getExceptionModel(HttpStatus httpStatus, CommonException ex) {
//        ExceptionModel exceptionModel = new ExceptionModel();  
//        ErrorENUM errorEnum = ex.getErrorEnum();  
//        exceptionModel.setStatus(httpStatus.value());  
//        exceptionModel.setMoreInfo(ex.getMoreInfo());
//        if (errorEnum != null) {  
//            exceptionModel.setErrorCode(errorEnum.getCode());  
//            exceptionModel.setMessage(errorEnum.toString());  
//        }  
//        return exceptionModel;  
//    }  
//  
//    private void handleLog(HttpServletRequest request, Exception ex) {  
//        Map parameter = request.getParameterMap();  
//        StringBuffer logBuffer = new StringBuffer();  
//        if (request != null) {  
//            logBuffer.append("  request method=" + request.getMethod());  
//            logBuffer.append("  url=" + request.getRequestURL());  
//        }  
////        if (ex instanceof CommonException) {
////            logBuffer.append("  moreInfo="  
////                    + ((CommonException) ex).getMoreInfo());  
////        }  
//        if (ex != null) {  
//            logBuffer.append("  exception:" + ex);  
//        }  
//        logger.error(logBuffer.toString());  
//    }
}
