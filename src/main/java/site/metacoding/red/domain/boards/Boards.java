package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.web.dto.request.boards.UpdateDto;

@Getter
@Setter
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId;
	private Timestamp createdAt; // At은 시분초 표현. Dt 년 월 일 
	
	public void update(UpdateDto updateDto) {
		this.title = updateDto.getTitle();
		this.content = updateDto.getContent();
	}
	public Boards(String title, String content,Integer usersId) {
		this.usersId = usersId;
		this.title = title;
		this.content = content;
	}
	
	
}
