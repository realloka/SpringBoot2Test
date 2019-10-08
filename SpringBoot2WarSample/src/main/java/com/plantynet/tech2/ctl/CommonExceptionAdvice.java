package com.plantynet.tech2.ctl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.plantynet.common.exception.JsonException;

@ControllerAdvice//(annotations = Controller.class)
public class CommonExceptionAdvice
{
	//http://wonwoo.ml/index.php/post/2208/amp
	
    /**
     * json 에러 처러
     * @param ex
     * @return
     */
    @ExceptionHandler(JsonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> handleJsonException(Exception ex)
    {
        Map<String, String> map = new HashMap<>();
        
        map.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        map.put("message", ex.getMessage());
        
        return map;
    }
    
    /**
     * 에러 처러
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String handleException2(Exception ex, Model model)
    {
    	model.addAttribute("exception", ex);
    	
    	return "error/error_default";
    }
}
