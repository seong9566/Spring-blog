package site.metacoding.red.web.request.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDto {
	private String password;
	private String email;
}
