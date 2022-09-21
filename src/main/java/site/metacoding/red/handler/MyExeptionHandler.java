package site.metacoding.red.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import site.metacoding.red.handler.ex.MyApiException;
import site.metacoding.red.handler.ex.MyException;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.response.CMRespDto;

@ControllerAdvice
public class MyExeptionHandler {
	
	@ExceptionHandler(MyApiException.class)// runtime은 실행 중 오류
	public @ResponseBody CMRespDto<?> apiError(Exception e){
		return new CMRespDto<>(-1,e.getMessage(), null);// 에러가 나면 응답 해줄 데이터 
	}
	
	
	@ExceptionHandler(MyException.class)// runtime은 실행 중 오류
	public @ResponseBody String m1(Exception e){
		return Script.back(e.getMessage());// 에러가 나면 응답 해줄 데이터 
	}
	

}
