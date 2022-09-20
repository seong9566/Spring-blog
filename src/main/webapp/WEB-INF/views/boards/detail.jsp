<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<br />
		<input id ="page" type = "hidden" value="${sessionScope.referer.page}">
	<input id = "keyword" type = "hidden" value="${sessionScope.referer.keyword}">
	<div class="d-flex">
		<input id="id" type="hidden" value="${detailDto.id}" />
		<input id = "lovesId" type="hidden" value="${detailDto.lovesId }"/>
			<form>
				<a href="/boards/${detailDto.id}/updateForm" class="btn btn-warning">수정하러가기</a>
			</form>
			<form>
				<button id ="btnDelete" type="button"class="btn btn-outline-danger">삭제</button>
			</form>
		</div>
	<div class="d-flex justify-content-between">
		<h3>${detailDto.title}</h3>
		<div>
			좋아요수 : <span id="countLove">${detailDto.loveCount}</span> 
			<i id="iconLove" class='${detailDto.loved ? "fa-solid" : "fa-regular"} fa-heart my_pointer my_red'></i>
		</div>
	</div>
	<hr/>
	<div>${detailDto.content}</div>
</div>
<script>

$("#btnDelete").click(()=>{
	deleteById();
});

function deleteById(){
	let id = $("#id").val();
	
	let page = $("#page").val();
	let keyword = $("#keyword").val();
	
	$.ajax("/boards/" + id, {
		type: "DELETE",
		dataType: "json" // 응답 데이터
	}).done((res) => {
		if (res.code == 1) {
			//location.href = document.referrer;
			location.href = "/?page="+page+"&keyword="+keyword;  //  /?page=?&keyword=?
		} else {
			alert("글삭제 실패");
		}
	});
}

// 하트 아이콘을 클릭했을때의 로직
$("#iconLove").click(()=>{
	let isLovedState = $("#iconLove").hasClass("fa-solid");
	if(isLovedState){
		deleteLove();
	}else{
		insertLove();
	}
});

// DB에 insert 요청하기
function insertLove(){
	let id = $("#id").val();
	
	$.ajax("/boards/"+id+"/loves", {
		type: "POST",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			renderLoves();
			// 좋아요 수 1 증가
			let count = $("#countLove").text();
			$("#countLove").text(Number(count)+1);
			$("#lovesId").val(res.data.id);
			//console.log(res);
		}else{
			alert("좋아요 실패했습니다");
		}
	});
}

// DB에 delete 요청하기
function deleteLove(){
      let id = $("#id").val();
      let lovesId = $("#lovesId").val();
      
      $.ajax("/boards/"+id+"/loves/"+lovesId, {
         type: "DELETE",
         dataType: "json"
      }).done((res) => {
         if (res.code == 1) {
            renderCancelLoves();
            let count = $("#countLove").text(); // 좋아요 갯수를 가져온다.
            $("#countLove").text(Number(count)-1); // -1해줌. -> 통신이 성공하고 해야 함.
         }else{
            alert("좋아요 취소에 실패했습니다");
         }
      });
   }

// 빨간색 하트 그리기
function renderLoves(){
	$("#iconLove").removeClass("fa-regular");
	$("#iconLove").addClass("fa-solid");
}

// 검정색 하트 그리기
function renderCancelLoves(){
	$("#iconLove").removeClass("fa-solid");
	$("#iconLove").addClass("fa-regular");
}
</script>
<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>