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

// 하트 아이콘을 클릭했을때의 로직
$("#iconLove").click(() => {
	clickLove();
});

$("#btnSave").click(() => {
	save();
});



// =====================함수 ==================

function save() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val(),
	};

	$.ajax("/s/boards", {
		type: "POST",
		dataType: "JSON",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		}
	});
}


function deleteBoard() {
	let id = $("#id").val();
	let page = $("#page").val();
	let keyword = $("#keyword").val();

	$.ajax("/s/boards/" + id, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/?page=" + page + "&keyword=" + keyword; //?page=?&keyword=?
		}
		else {
			alert("게시글 삭제 실패");
		}
	});
}

function updateBoard() {
	let id = $("#id").val();

	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};


	$.ajax("/s/boards/" + id, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("게시글 수정 완료");
			location.href = "/boards/" + id;

		} else {
			alert("수정 실패");

		}
	});
}


// 빨간색 하트 그리기
function renderLoves() {
	$("#iconLove").removeClass("fa-regular");
	$("#iconLove").addClass("fa-solid");
}

// 검정색 하트 그리기
function renderCancelLoves() {
	$("#iconLove").removeClass("fa-solid");
	$("#iconLove").addClass("fa-regular");
}
function clickLove() {
	let isLovedState = $("#iconLove").hasClass("fa-solid");
	if (isLovedState) {
		deleteLove();
	} else {
		insertLove();
	}
}
// Love DB에 insert 요청하기
function insertLove() {
	let id = $("#id").val();

	$.ajax("/s/boards/" + id + "/loves", {
		type: "POST",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			renderLoves();
			// 좋아요 수 1 증가
			let count = $("#countLove").text();
			$("#countLove").text(Number(count) + 1);
			$("#lovesId").val(res.data.id);
			//console.log(res);
		} else {
			alert("좋아요 실패했습니다");
		}
	});
}
// DB에 delete 요청하기
function deleteLove() {
	let id = $("#id").val();
	let lovesId = $("#lovesId").val();

	$.ajax("/s/boards/" + id + "/loves/" + lovesId, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			renderCancelLoves();
			let count = $("#countLove").text(); // 좋아요 갯수를 가져온다.
			$("#countLove").text(Number(count) - 1); // -1해줌. -> 통신이 성공하고 해야 함.
		} else {
			alert("좋아요 취소에 실패했습니다");
		}
	});
}
