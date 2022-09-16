<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br />
	
	<div class="d-flex">
			<form>
				<a class="btn btn-outline-warning"  href="/boards/${boards.id}/updateForm">수정 하기</a>
			</form>

			<form action="/boards/${boards.id}/delete" method="post">
				<button class="btn btn-outline-danger">삭제</button>
			</form>
		</div>
	<div>
		<h2>${boards.title}</h2>
	</div>
	<hr/>

	<div>${boards.content }</div>


</div>

<%@ include file="../layout/footer.jsp"%>

