<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<br /> <input id="page" type="hidden" value="${sessionScope.referer.page}"> <input
		id="keyword" type="hidden" value="${sessionScope.referer.keyword}">
	<div class="d-flex">
		<input id="id" type="hidden" value="${detailDto.id}" /> <input id="lovesId" type="hidden"
			value="${detailDto.lovesId }" />
		<c:if test="${!empty sessionScope.principal}">
			<form>
				<a href="/s/boards/${detailDto.id}/updateForm" class="btn btn-warning">수정하러가기</a>
			</form>
			<form>
				<button id="btnDelete" type="button" class="btn btn-outline-danger">삭제</button>
			</form>
		</c:if>



	</div>
	<div class="d-flex justify-content-between">
		<h3>${detailDto.title}</h3>
		<div>
			좋아요수 : <span id="countLove">${detailDto.loveCount}</span> <i id="iconLove"
				class='${detailDto.loved ? "fa-solid" : "fa-regular"} fa-heart my_pointer my_red'></i>
		</div>
	</div>
	<hr />
	<div>${detailDto.content}</div>
</div>
<script>
	
</script>
<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>