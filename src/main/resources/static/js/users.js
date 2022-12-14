/**
 * 쿠키는 자바스크립으로 접근 불가능, 자기 자신의 영역(브라우저 영역)
 브라우저에 저장  -> 쿠키 (브라우저가 꺼지면 날라간다)
 서버에 저장 -> 세션 (서버가 꺼지면 세션이 날라간다)
 조금 더 영구적인 저장 -> DB(영구적인 저장영역)
 */

let isUsernameSameCheck = false;
let isPasswordCheck = false;

// 패스워드 체크 
$("#btnPasswordCheck").click(() => {
	checkPassword();
});


//회원가입
$("#btnJoin").click(() => {
	join();
});

//유저네임 중복체크 
$("#btnUserNameSameCheck").click(() => {
	checkUserName();
});

// 로그인
$("#btnLogin").click(() => {
	login();

});

// 회원 삭제
$("#btnDelete").click(() => {
	resign();
});

// 회원 정보 수정
$("#btnUpdate").click(() => {
	update();
});
// =======================함수============================
// username에 한글 체크 
function koreanCheck() {
	let username = $("#username").val();
	let korRule = /^[가-힣]*$/;
	if (korRule.test(username)) {
		return true;
	} else {
		return false;
	}
}
// 대문자 체크
function capitalCheck() {
	let username = $("#username").val();
	let checkcapital = /[A-Z]/;
	if (checkcapital.test(username)) {
		return false;
	}
	else {
		return true;
	}
}
// 이메일 형식 체크 
function emailCheck() {
	let email = $("#email").val();
	let checkText = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	if (checkText.test(email)) {
		return true; // 정규표현식이면 true
	}
	else {
		return false;//아니면 false
	}
}
//이메일 공백 체크 
function spaceEmail() {
	let checkspace = /\s/g;
	let email = $("#email").val();
	if (checkspace.test(email)) {
		return true;
	}
	else {
		return false;
	}
}
//이메일 한글 체크 
function koreanCheckEmail() {
	let email = $("#email").val();
	let korRule = /^[가-힣]*$/;
	if (korRule.test(email)) {
		return true;
	} else {
		return false;
	}
}
//유저이름 공백 체크
function spaceUsername() {
	let checkspace = /\s/g;
	let username = $("#username").val();
	if (checkspace.test(username)) {
		return true;
	}
	else {
		return false;
	}
}
//패스워드 공백 체크
function spacePassword() {
	let checkspace = /\s/g;
	let password = $("#password").val();
	if (checkspace.test(password)) {
		return true;
	}
	else {
		return false;
	}
}
// 패스워드 한글 체크 
function koreanCheckPassword() {
	let password = $("#password").val();
	let korRule =  /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	if (korRule.test(password)) {
		return true;
	} else {
		return false;
	}
}

// 회원 가입 함수
function join() {
	if (isUsernameSameCheck == false) {
		alert("유저네임 중복 체크를 진행 해주세요");
		return;
	}

	if (koreanCheck() == true || spaceUsername() == true) {
		alert("username에 한글 또는 공백을 넣지마세요");
		return;
	}

	if (capitalCheck() == true) {
		alert("username에 대문자 하나이상을 입력하세요.");
		return;
	}

	if (spacePassword() == true || koreanCheckPassword() == true) {
		alert("비밀번호에 공백이나 한글을 넣지마세요 .");
		return;
	}

	if (isPasswordCheck == false) {
		alert("비밀번호 체크를 진행 해주세요.");
		return;
	}

	if (emailCheck() == false || spaceEmail() == true || koreanCheckEmail() == true) {
		alert("이메일형식이 올바르지 않습니다.");
		return;
	}


	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};
	$.ajax("/api/join", {
		type: "POST",
		dataType: "JSON",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/loginForm";
		}
		else {
			alert(res.msg);
			history.back;
		}
	});
}

// 패스워드 체크 
function checkPassword() {
	let passwordCheck = $("#passwordCheck").val();
	let password = $("#password").val();
	if (password == passwordCheck) {
		alert("패스워드가 일치합니다");
		isPasswordCheck = true;
	}
	else {
		alert("패스워드가 일치하지 않습니다.");
		return;
	}
}

// 유저 네임 중복 체크 
function checkUserName() {
	let username = $("#username").val();
	$.ajax(`/api/users/usernameSameCheck?username=${username}`, {// JS이므로 백틱 사용 가능  
		type: "GET",
		dataType: "json",
		async: true
	}).done((res) => {
		if (res.code == 1) {
			if (res.data == false) {
				alert("아이디 중복 되지 않았습니다.");
				isUsernameSameCheck = true;
			}
			else {
				alert("아이디가 중복 되었습니다. 다른 아이디 사용해주세요")
				isUsernameSameCheck = false;
				$("#username").val("");
			}
		}

	});
}



//로그인
function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember: $("#remember").prop("checked")
	};

	$.ajax("/api/login", {
		type: "POST",
		dataType: "JSON",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		}
		else {
			alert("로그인 실패, 아이디 패스워드를 확인해주세요");
		}
	});
}

// 회원 탈퇴
function resign() {
	let id = $("#id").val();

	$.ajax("/s/api/users/" + id, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴 완료");
			location.href = "/";
		} else {
			alert("회원탈퇴 실패");
		}
	});
}

// 회원 정보 수정
function update() {
	let data = {
		password: $("#password").val(),
		email: $("#email").val()
	};


	let id = $("#id").val();

	$.ajax("/s/api/users/" + id, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("회원 수정 완료");
			location.reload();
		} else {
			alert("업데이트에 실패하였습니다");
		}
	});


}

