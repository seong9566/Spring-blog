/**
 * 
 */

//좋아요 클릭 
$("#iconHeart").click((event) => { // event를 쓰면 클릭된 부분의 정보를 가져옴
	let check = $("#iconHeart").hasClass("fa-regular");

	if (check == true) {
		$("#iconHeart").removeClass("fa-regular");
		$("#iconHeart").addClass("fa-solid");
		$("#iconHeart").css("color", "red");
	}
	else {
		$("#iconHeart").removeClass("fa-solid");
		$("#iconHeart").addClass("fa-regular");
		$("#iconHeart").css("color", "black");
	}
});



//삭제
$("#btnDelete").click(() => {
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
});

//수정
$("#btnUpdate").click(() => {
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
});