package site.metacoding.red.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import site.metacoding.red.domain.users.Users;

public class LoginIntercepter implements HandlerInterceptor{
	
	@Override
	public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			HttpSession session = request.getSession();
			Users principal = (Users)session.getAttribute("principal");
			if(principal == null) {
				response.sendRedirect("/loginForm");
				return false; // 세션 확인후 널이면 인터셉터해서 진입 못하게 막음 
			}
			System.out.println("메서드 실행===================");
		return true;
	}
}
