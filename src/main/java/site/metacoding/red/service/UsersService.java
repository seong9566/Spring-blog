package site.metacoding.red.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Service
public class UsersService {
	private final UsersDao usersDao;
	private final BoardsDao boardsDao;
	
	public void 회원가입(JoinDto joinDto) { // username, password, email -> id,creatAt은 받지 않으므로 Dto만들어서 넘겨준다.
		// 클라이언트가 Dto를 요청 -> Service에서 Entity로 바꾸어 수행
		// 1. Dto를 엔티티로 변경하는 코드
		// 2. 엔티티로 insert 
		usersDao.join(joinDto.toEntity());
	}
	
	public Users 로그인(LoginDto loginDto) {// Dto username, password
		
		Users usersPS = usersDao.findByUsername(loginDto.getUsername());
		if(usersPS == null) {
			return null;
		}
		
		// 1. if로 usersPs의 password와 Dto password 비교
		if(usersPS.getPassword().equals(loginDto.getPassword())) {
			return usersPS;
		}
		else {
		return null;
		}
	}


	public Users 회원수정(Integer id, UpdateDto updateDto) {// id, 디티오(password, email)
		// 1. 영속화 
		Users usersPS = usersDao.findById(id);
		// 2. 영속화 된 객체 변경
		usersPS.updatePasswordEmail(updateDto);
		// 3. DB 수행 update
		usersDao.update(usersPS);
		return usersPS;
	} 
	/*
	 *   1. deleteById가 실행 할 때 트랜잭션이 실행된다
	 *   2. delete가 끝나면 그 트랜젝션을 타고 아래 boards가 들어와서 실행된다
	 *   3. 여기서 실패 할 시 트랜젝션 종료하고 rollback
	 * */
	@Transactional(rollbackFor = RuntimeException.class) // 트랜젝션이 실패 하면 rollback 한다.  
	public void 회원탈퇴(Integer id) { //users - delete, boards - update 모두 연결 users 삭제 -> boards의 usersid를 null로 바꾼다.
		/**
		 * public void 로그아웃() {} 로그아웃은 DB와 연결되는 것이 아니기 때문에 컨트롤러에서 만들어준다.
		 * 톰캣에서 만들어주는 response , request가 생성되는 것은 모두 서비스에 넘겨주지 말아야한다. 
	 	 * 즉 DB와 연관 없는 것은 Service에 작성 하지 않는다.
		**/	
		usersDao.deleteById(id);
		boardsDao.updateByUsersId(id);// mybaties에서 for문 돌려서 업데이트 
	}

	
	
	public boolean 아이디중복확인(String username) {
		Users usersPS = usersDao.findByUsername(username);
		// 있으면 true, 없으면 false 리턴 
		if(usersPS == null) {
			return false;
		}else { 
			return true;
		}
	}
	
	public Users 회원정보보기(Integer id) {
		Users usersPS = usersDao.findById(id);
		return usersPS;
	}
}
