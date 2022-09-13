package site.metacoding.red.web.request.users;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.domain.users.Users;

@Getter
@Setter

public class InsertDto {
	private String username;
	private String password;
	private String email;
	
	public Users toEntity() {
		Users users = new Users(this.username, this.password, this.email);
		return users;
	}
}
