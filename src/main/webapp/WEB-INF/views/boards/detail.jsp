<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br />
	
	<div class="d-flex">
	<input id="id" type="hidden" value = "${boards.id}">
			<form>
				<button id = "btnUpdate"type="button"class="btn btn-outline-warning">게시글 수정</button>
			</form>
			<form>
				<button id = "btnDelete" type="button"class="btn btn-outline-danger">삭제</button>
			</form>
	</div>
	<hr/>	
	<div class = "d-flex justify-content-between">
		<input id="title" type="text" class="form-control" placeholder="Enter title" value="${boards.title}">
		<div >좋아요 수 : 10 <i class="fa-regular fa-heart" id = "iconHeart"></i></div>
	</div>
	
	<br/>

	<div>
	<textarea id="content" class="form-control" rows="8">${boards.content}</textarea>
	
	</div>


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
	


	$("#btnDelete").click(()=>{
		let id = $("#id").val();
		
		$.ajax("/boards/"+id,{
			type: "DELETE",
			dataType: "json"
		}).done((res)=>{
			if(res.code == 1){
				alert("게시글 삭제 성공");
				location.href = "/boards";
			}
			else{
				alert("게시글 삭제 실패");
			}
		});
	});
	

	$("#btnUpdate").click(()=>{
		let id = $("#id").val();
		
		let data={
				title: $("#title").val(),
				content: $("#content").val()
		};
		
		
		$.ajax("/boards/"+id,{
			type: "PUT",
			dataType:"json",
			data: JSON.stringify(data),
			headers: {
				"Content-Type": "application/json; charset=utf-8"
			}
		}).done((res)=>{
			if (res.code == 1) {
					alert("게시글 수정 완료");
					location.reload(); 
				
			} else {
				alert("수정 실패");
				
			}
		});
	});
</script>

  <script>
  	$("#title").css({
  			width:600
  			});
  	
      $('#content').summernote({
        height: 400
      });
    </script>
<%@ include file="../layout/footer.jsp"%>

