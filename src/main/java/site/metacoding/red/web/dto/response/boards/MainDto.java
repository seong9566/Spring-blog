package site.metacoding.red.web.dto.response.boards;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDto {
	private Integer id;
	private String title;
	private String username;
	
//	public String getUsername() {
//		return username == null ? "익명" : username;
//	}
}
