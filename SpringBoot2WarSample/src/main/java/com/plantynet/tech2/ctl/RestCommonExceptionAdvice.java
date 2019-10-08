package com.plantynet.tech2.ctl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.plantynet.common.exception.JsonException;

//component scan으로 명시적으로 지정하지 않으려면 Application 하위 패키지에 넣어야 함. 안 그럼 인식 안 함.
//@ControllerAdvice를 따로 정의 하면 이 advice는 실행되지 않는 것 같다. 
//annotation으로 구분될줄 알았는데... R
//@RestController 주석 자체가 @Controller이기 때문에 Spring이 @ControllerAdvice annotation = Controller.class를 모두 고려하기 때문에...
//base package 로 구분하거나 해야 할 듯..., assignableTypes 등으로 처리
@RestControllerAdvice(assignableTypes=RestApiController.class)
//==> ResponseEntityExceptionHandler 상속 받아서 처리하는 방법도 있는데 그렇게 되면 405, 404 등에 대해서 처리가 가능해지지만 대신 json이냐 html이냐에 따라 다르게 처리해야 함
public class RestCommonExceptionAdvice
{
    //https://www.slipp.net/questions/600
	
    /**
     * json 에러 처러
     * @param ex
     * @return
     */
    @ExceptionHandler(value={JsonException.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleJsonException(Exception ex)
    {
        Map<String, String> map = new HashMap<>();
        
        map.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        map.put("message", ex.getMessage());
        
        return map;
    }
}
