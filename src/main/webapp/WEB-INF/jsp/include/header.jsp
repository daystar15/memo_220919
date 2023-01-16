<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 class="logo font-weight-bold">
	메모게시판
</h1>  
<!-- 로그인 정보: 로그인이 되었을 때만 노출 -->
<c:if test="${not empty userId}">
	<div>
		<span class="mr-3">${userName}님 환영합니다</span>
		<a href="/user/sign_out">로그아웃</a>
	</div>
</c:if>