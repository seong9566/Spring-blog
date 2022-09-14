package site.metacoding.red.web.dto.request.boards;

import lombok.Getter;

import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;

@Getter
@Setter

public class WriteDto {
	private String title;
	private String content;
	
	// 엔티티화 
	public Boards toEntity(Integer usersId) {
		Boards boards = new Boards(this.title, this.content, usersId);
		return boards;
	}
}
