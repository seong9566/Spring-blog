package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@RestController
public class UsersController {
	private final HttpSession session;
	private final UsersService usersService;
	private final UsersDao usersDao;

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
	public String join(JoinDto joinDto) {
		usersService.회원가입(joinDto);
		return "redirect:/loginForm";// 회원가입이 되면 로그인 폼으로 이동
	}
	
	@PostMapping("/login")
	public @ResponseBody String join(LoginDto loginDto) {
		Users principal = usersService.로그인(loginDto);
		if(principal == null) {
			return Script.back("아이디 혹은 비밀번호가 틀렸습니다.");
		}
		session.setAttribute("principal", principal);
		return Script.href("/");
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
