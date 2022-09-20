package site.metacoding.red.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.red.web.dto.response.CMRespDto;
/*1. 인터셉터 하나 더 생성
2. 인터셉터 등록 /** (모든 주소)
3. http body 에 "최주호바보"
BufferedReader br = request.getReader();
String s = "";
while(true)
    s = s + br.readLine();

if(s.contains("바보")){
   
}*/
public class TestIntercepter implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			BufferedReader br = request.getReader(); //body에 담긴 데이터를 읽음
			StringBuilder sb = new StringBuilder();// 버퍼 리더에 데이터를 담기 위해 사용
			
			while(true) {
				String temp = br.readLine();
				if(temp == null) { break;}
				sb.append(temp);
			}
			
			if(sb.toString().contains("바보야")) {
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				CMRespDto<?> cmRespDto = new CMRespDto<>(-1,"바보 입력됌",null);
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(cmRespDto);
				out.println(json);
				return false;
			}
			
		return true;
	}

}
