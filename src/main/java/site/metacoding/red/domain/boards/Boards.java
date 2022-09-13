package site.metacoding.red.domain.boards;

import java.sql.Time;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId;
	private Timestamp createdAt; // At은 시분초 표현. Dt 년 월 일 
}
