<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id = "username"type="text" class="form-control" placeholder="Enter username" >
			<button id= "btnUserNameSameCheck"class = "btn btn-warning" type="button">UserName 중복 체크</button>
		</div>
		<div class="mb-3">
			<input id="password"type="password" class="form-control" placeholder="Enter password"maxlength=20>
			<input id="passwordCheck"type="password" class="form-control" placeholder="Onemore password" maxlength=20>
			<button id= "btnPasswordCheck"class = "btn btn-warning" type="button">password 체크</button>
		</div>
		<div class="mb-3">
			<input id="email"type="email" class="form-control" placeholder="Enter email" maxlength=20>
		</div>
		<button id = "btnJoin"type="button" class="btn btn-primary">회원가입</button>
	</form>
</div>

<script>

</script>
<script src="/js/users.js"></script>
<%@ include file="../layout/footer.jsp"%>

