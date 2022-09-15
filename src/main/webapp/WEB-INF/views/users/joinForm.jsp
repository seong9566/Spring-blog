<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id = "username"type="text" class="form-control" placeholder="Enter username">
			<button id= "btnUserNameSameCheck"class = "btn btn-warning" type="button">UserName 중복 체크</button>
		</div>
		<div class="mb-3">
			<input id="password"type="password" class="form-control" placeholder="Enter password">
			<input id="passwordCheck"type="password" class="form-control" placeholder="Onemore password">
			<button id= "btnPasswordCheck"class = "btn btn-warning" type="button">password 체크</button>
		</div>
		<div class="mb-3">
			<input id="email"type="email" class="form-control" placeholder="Enter email">
		</div>
		<button id = "btnJoin"type="button" class="btn btn-primary">회원가입</button>
	</form>
</div>

<script>
	// isUsername이 false일 경우만 회원가입 가능 하기 위한 변수 
	let isUsernameSameCheck = false;
	// isPasswordCheck를 진행 하지 않으면 회원가입 불가능
	let isPasswordCheck = false;
	
	$("#btnPasswordCheck").click(()=>{
		let passwordCheck = $("#passwordCheck").val();
		let password = $("#password").val();
		if(password == passwordCheck){
			alert("패스워드가 일치합니다");
			isPasswordCheck = true;
		}
		else{
			alert("패스워드가 일치하지 않습니다.");
			return;
		}
	});
	//회원가입
	$("#btnJoin").click(()=>{
		if(isUsernameSameCheck == false){
			alert("유저네임 중복 체크를 진행 해주세요");
			return;
		}
		if(isPasswordCheck == false){
			alert("비밀번호 체크를 진행 해주세요.");
			return;
		}
		
		//0. 통신 오브젝트 생성
		let data ={
				username : $("#username").val(),
				password : $("#password").val(),
				email : $("#email").val()
		};
		$.ajax("/join",{
			type: "POST",
			dataType: "JSON",
			data: JSON.stringify(data),// 데이터 전송할 때 JSON데이터로 전송
			headers: { // Body 데이터를 전송 하기 위해선 contentType이 필요
				"Content-Type" : "application/json"
			}
		}).done((res)=>{
			if(res.code ==1){// 회원가입 성공 하면 main 페이지로 이동 
				location.href = "/loginForm";
			}
		});
	});
	
	//유저네임 중복체크 
	$("#btnUserNameSameCheck").click(()=>{
		let username = $("#username").val();
		$.ajax("users/usernameSameCheck?username="+username,{// ``백틱을 사용은 하면 EL표현식으로 인식 하기 때문에 백틱 X
			type:"GET",
			dataType: "json", // 아무것도 적지 않으면 default는 json이다
			async:true // 이벤트는 무조건 true를 해줘야한다! 
		}).done((res)=>{// 함수는 람다식 사용
			console.log(res);
			if(res.code == 1){
				//alert("통신성공");
				if(res.data == false){
					alert("아이디 중복 되지 않았습니다.");
					isUsernameSameCheck = true;// 중복 체크 완료  
				}
				else{
					alert("아이디가 중복 되었습니다. 다른 아이디 사용해주세요")
					isUsernameSameCheck = false;
					 $("#username").val("");//set으로 현재 view에적혀진 id를 비워줌
				}
			}
			
		}); // ajax함수로 통신 시작 done()은 응답
		
	});
</script>
<%@ include file="../layout/footer.jsp"%>

