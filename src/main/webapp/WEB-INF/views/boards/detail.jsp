<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<br />
	
	<div class="d-flex">
		<input id="id" type="hidden" value="${boards.id}">
			<form>
				<a class="btn btn-outline-warning"  href="/boards/${boards.id}/updateForm">수정 하기</a>
			</form>
			<form>
				<button id ="btnDelete" type="button"class="btn btn-outline-danger">삭제</button>
			</form>
		</div>
	<div class = "d-flex justify-content-between">
		<h2>${boards.title}</h2>
		<div >좋아요 수 : 10 <i class="fa-regular fa-heart" id = "iconHeart"></i>
		</div>
	</div>
	<hr/>
	<div>${boards.content }</div>
</div>
<script>
	$("#iconHeart").click((event)=>{ // event를 쓰면 클릭된 부분의 정보를 가져옴
		let check = $("#iconHeart").hasClass("fa-regular");
		
		if(check == true){
			$("#iconHeart").removeClass("fa-regular");
			$("#iconHeart").addClass("fa-solid");
			$("#iconHeart").css("color", "red");
		}
		else{
			$("#iconHeart").removeClass("fa-solid");
			$("#iconHeart").addClass("fa-regular");
			$("#iconHeart").css("color", "black");
		}
	});
</script>
<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>