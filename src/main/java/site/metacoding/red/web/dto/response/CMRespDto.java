package site.metacoding.red.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//유저네임 중복 확인을 위한 클래스 
@AllArgsConstructor
@Getter
public class CMRespDto<T> {
	private Integer code; // 1. 정상 , -1 실패
	private String msg; // 실패의 이유, 성공한 이유
	private T data; // 데이터의 응답 타입은 항상 다르기 때문에 제네릭 타입을 준다. <T>
	
}
