package site.metacoding.red.web.dto.response.boards;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDto {
	private boolean isNotResult;
	private String keyword;
	private Integer blockCount;// 한 블럭에서 페이지 갯수(5) 1-5, 6-10,
	private Integer currentBlock; // 현재 블럭 위치 1~5 , 다음 누르면 6~10 에 위치
	private Integer startPageNum;// 현재 블럭에서 시작 페이지 num
	private Integer lastPageNum; // 현재 블럭에서 마지막 페이지 num
	private Integer totalCount; // 전체 글의 수 
	private Integer totalPage; // 전체 페이지 수 
	private Integer currentPage; // 현재 페이지 
	private boolean isLast;
	private boolean isFirst;
	
	
	private List<MainDto> mainDtos;
	
	public void makeBlockInfo(String keyword) {
		this.keyword = keyword;
		this.blockCount = 5; // 검색을 해도 한 블럭에 페이지 갯수는 5
		this.currentBlock = currentPage / blockCount;
		this.startPageNum = 1 + blockCount * currentBlock;
		this.lastPageNum = 5+ blockCount * currentBlock;
		
		if(totalPage < lastPageNum) {
			this.lastPageNum = totalPage;
		}
		
	}

}
