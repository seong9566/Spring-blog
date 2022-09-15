package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	private final HttpSession session;
	private final UsersService usersService;
	private final UsersDao usersDao;

	//http://localhost:8000/users/usernameSameCheck?username=ssar
	@GetMapping("users/usernameSameCheck")
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
	public String loginForm() { // 쿠키 배워보기
		return "users/loginForm";
	}

	@PostMapping("/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody JoinDto joinDto) {// @RequestBody : Dto를 JSON으로 받기 위함 
		usersService.회원가입(joinDto);
		return new CMRespDto<>(1,"회원가입 성공",null);// 회원가입이 되면 로그인 폼으로 이동
	}
	
	@PostMapping("/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginDto loginDto) {
		Users principal = usersService.로그인(loginDto);
		if(principal == null) {
			return new CMRespDto<>(-1, "로그인 실패", null);
		}
		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "로그인 성공", null);
	}
	
	@GetMapping("/users/{id}")
	public String updateForm(@PathVariable Integer id,Model model) {
		// 페이지를 줄 때는 업데이트가 처음에 select 를 해주기 때문에 model 필요
		Users usersPS = usersService.회원정보보기(id);
		model.addAttribute("users", usersPS);
		return "users/updateForm";
	}
	
	@PutMapping("/users/{id}")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		usersService.회원수정(id, updateDto);
		// 메세지를 뿌리면서 가고 싶다면 @ResponseBody를 넣어주면 된다. 
		return "redirect:/users/"+id;
	}
	
	// 회원 탈퇴는 updateForm 아래에 버튼을 만들기 
	@DeleteMapping("/users/{id}")
	public @ResponseBody String delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		return Script.href("/loginForm","회원탈퇴가 완료 되었습니다.");
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}
}
