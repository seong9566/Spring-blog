package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsService boardsService;
	private final HttpSession session;

	//게시글 목록 보기
	@GetMapping({"/","/boards"})
	public String getBoards(Model model,Integer page, String keyword) {
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingDto", pagingDto);
		return "/boards/main";
	}
	
	// 글쓰기 폼 이동 -> usersPS 값이 필요 하므로 Get 맵핑으로 가져옴.
	@GetMapping("boards/writeForm") // 인증이 필요할 땐 앞에 boards처럼 식별자 붙인다.
	public String writeForm() {
		Users usersPS = (Users) session.getAttribute("principal");
		if (usersPS == null) {
			return "redirect:/loginForm";
		}
		return "boards/writeForm";
	}
	// 글쓰기 
	@PostMapping("/boards")
	public @ResponseBody CMRespDto<?> writeBoards(@RequestBody WriteDto writeDto) {
		Users usersPS = (Users) session.getAttribute("principal");
		boardsService.게시글쓰기(writeDto, usersPS);
		return new CMRespDto<>(1, "글쓰기 성공", null);
	}
	
	// 게시글 상세보기 
	@GetMapping("boards/{id}")
	public String getBoardsDetail(@PathVariable Integer id,Model model) {
		model.addAttribute("boards", boardsService.게시글상세보기(id));
		return "/boards/detail";
	}
	
	//게시글 수정하기 폼으로 이동
	@GetMapping("/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id,Model model) {
		Boards boardsPS = boardsService.게시글상세보기(id);
		model.addAttribute("boards",boardsPS);
		return "boards/updateForm";
		
	}
	//게시글 수정하기
	@PutMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id,@RequestBody UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		return new CMRespDto<>(1, "수정 성공", null);
	}
	
	//게시글 삭제하기
	@DeleteMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteBoards(@PathVariable Integer id) {
		boardsService.게시글삭제하기(id);
		return new CMRespDto<>(1, "삭제 성공", null);
	}
	
}
