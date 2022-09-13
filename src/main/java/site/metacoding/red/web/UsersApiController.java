package site.metacoding.red.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@RestController
public class UsersApiController {
	
	private final UsersService usersService;
	
	
	@PostMapping("/api/users/insert")
	public String insert(JoinDto joinDto) {
		usersService.회원가입(joinDto);
		return "회원가입 완료";
		}
	
	@PostMapping("/api/users/login")
	public String login(LoginDto loginDto) {
		Users usersPS = usersService.로그인(loginDto);
		if(usersPS == null) {
			return "로그인 실패";
		}
		else {
			return "로그인 성공";
		}
	}

	@GetMapping("/api/users/{id}")
	public Users getOne(@PathVariable Integer id) {
		return usersService.회원정보보기(id);
	}

	@PutMapping("/api/users/update/{id}")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		usersService.회원수정(id, updateDto);
		return "회원수정완료";
	}

	@DeleteMapping("/api/users/delete/{id}")
	public String delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		return "회원탈퇴완료";
	}

	// localhost:8000/api/users/usernameCheck?username=?
	@GetMapping("/api/users/usernameCheck")
	public String usernameSameCheck(String username) {
		boolean isSame = usersService.아이디중복확인(username);
		if(isSame) {
			return "유저네임이 중복";
		}else {
			return "유저네임이 중복이 되지않았어요";
		}
	}

}
