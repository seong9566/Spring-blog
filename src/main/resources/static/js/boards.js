/**
 * 
 */



//삭제
$("#btnDelete").click(() => {
 deleteBoard();
});

//수정
$("#btnUpdate").click(() => {
	updateBoard();
});

// =====================함수 ==================
function deleteBoard(){
		let id = $("#id").val();

	$.ajax("/boards/" + id, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			alert("게시글 삭제 성공");
			location.href = "/";
		}
		else {
			alert("게시글 삭제 실패");
		}
	});
}
function updateBoard(){
		let id = $("#id").val();

	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};


	$.ajax("/boards/" + id, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("게시글 수정 완료");
			location.href="/boards/"+id;

		} else {
			alert("수정 실패");

		}
	});
}