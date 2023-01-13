<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container w-25">
	<form method="post" action="/user/sign_in">
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">ID</span>
			</div>
			<input text="text" id="id" name="id" placeholder="Username" class="form-control">
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">PW</span>
			</div>
			<input text="password" id="password" name="password" class="form-control">
		</div>
		<input type="submit" class="btn btn-primary w-100" value="로그인">
		<a href="#" class="btn btn-dark d-block mt-4">회원가입</a>
	</form>
</div>