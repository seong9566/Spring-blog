package site.metacoding.red.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.handler.ex.MyApiException;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	private final HttpSession session;
	private final UsersService usersService;

	//http://localhost:8000/users/usernameSameCheck?username=ssar
	@GetMapping("/api/users/usernameSameCheck")
	// ResponseBody는 데이터를 리턴 하기때문에 붙여줌.
	public @ResponseBody CMRespDto<Boolean> usernameSaneCheck(String username) {
		// 현재 리턴 타입 boolean
		boolean isSame = usersService.아이디중복확인(username);
		
		// 리턴 타입은 항상 데이터로 한다. 
		return new CMRespDto<>(1,"통신 성공",isSame);// JSON응답 -> ajax통신에서 dataType 설정할때 필요 
	}
	
	// 인증이 필요한 기능은 앞에 (도메인)/users/라고 붙이고
	// 인증이 필요 없다면 PUT /login 처럼 붙이지 않는다.
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}

	@GetMapping("/loginForm")
	public String loginForm(Model model,HttpServletRequest request) { // 쿠키 가져가기
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("username")) {
				model.addAttribute(cookie.getName(),cookie.getValue());
			}
			System.out.println("==================");
			System.out.println(cookie.getName()); 
			System.out.println(cookie.getValue());
			System.out.println("==================");
		}
		return "users/loginForm";
	}

	@PostMapping("/api/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody @Valid JoinDto joinDto,BindingResult bindingResult) {// @RequestBody : Dto를 JSON으로 받기 위함 
		
		if(bindingResult.hasErrors()) {
			System.out.println("에러가 있습니다.");
			FieldError fe = bindingResult.getFieldError();
			throw new MyApiException(fe.getDefaultMessage());
		}
		else {
			System.out.println("에러가 없습니다.");
		}
		usersService.회원가입(joinDto);
		return new CMRespDto<>(1,"회원가입 성공",null);// 회원가입이 되면 로그인 폼으로 이동
	}
	
	@PostMapping("/api/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginDto loginDto,HttpServletResponse response ) {
		
//		System.out.println("====================");
//		System.out.println(loginDto.isRemember());
//		System.out.println("====================");
		
		// 직접 쿠키에 set으로 담아주는것은 기존의 쿠키를 날려버릴 수 있기 때문에 객체를 하나 생성해 뒤에 더해주는 방법을 사용한다. 
		if(loginDto.isRemember()) {
			// 쿠키 생성 
			Cookie cookie = new Cookie("username",loginDto.getUsername());
			cookie.setMaxAge(60*60*24);// 60초,60분 , 24시간 = 1일
			response.addCookie(cookie);
			//response.setHeader("Set-Cookie","username="+loginDto.getUsername()+";HttpOnly");
		}
		else {
			//쿠키 삭제 
			Cookie cookie = new Cookie("username", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		Users principal = usersService.로그인(loginDto);
		if(principal == null) {
			return new CMRespDto<>(-1, "로그인 실패", null);
		}
		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "로그인 성공", null);
	}
	
	//인증 필요 -> 인터셉터 
	@GetMapping("/s/users/{id}")
	public String updateForm(@PathVariable Integer id,Model model) {
		// 페이지를 줄 때는 업데이트가 처음에 select 를 해주기 때문에 model 필요
		Users usersPS = usersService.회원정보보기(id);
		model.addAttribute("users", usersPS);
		return "users/updateForm";
	}
	
	// 인증 필요 
	@PutMapping("/s/api/users/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		Users usersPS = usersService.회원수정(id, updateDto);
		session.setAttribute("principal", usersPS);// 세션 동기화 
		return new CMRespDto<>(1, "회원 수정 성공", null);
	}
	
	// 인증 필요 
	// 회원 탈퇴는 updateForm 아래에 버튼을 만들기 
	@DeleteMapping("/s/api/users/{id}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable Integer id,HttpServletResponse response) {
		usersService.회원탈퇴(id);
		session.invalidate(); // 세션 초기화 
		return new CMRespDto<>(1, "회원 탈퇴 성공", null);
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}
}
