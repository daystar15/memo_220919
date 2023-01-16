<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container w-25">
	<form method="post" action="/user/sign_in" id="loginForm">
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">ID</span>
			</div>
			<input text="text" id="loginId" name="loginId" placeholder="Username" class="form-control">
		</div>
		<div class="input-group mb-3">
			<div class="input-group-prepend">
				<span class="input-group-text">PW</span>
			</div>
			<input text="password" id="password" name="password" class="form-control">
		</div>
		<input type="submit" class="btn btn-primary w-100" value="로그인">
		<a href="/user/sign_up_view" class="btn btn-dark d-block mt-4">회원가입</a>
	</form>
</div>

<script>
	$(document).ready(function() {
		$('#loginForm').on('submit', function(e) {
			// 서브밋 기능 중단 -> 이벤트 객체 e가 있어야함
			e.preventDefault(); // 로그인 버튼을 눌러도 화면이 그대로 있다(아이디, 비번 입력안했을 때)
			
			// validation
			// return false;
			let loginId =  $('input[name=loginId]').val().trim();
			let password =  $('#password').val();
			
			if (loginId == '') {
				alert('아이디를 입력해주세요.');
				return false;
			}
			
			if (password == '') {
				alert('비밀번호를 입력해주세요.');
				return false;
			}
			
			// ajax (or 서브밋)
			let url = $(this).attr('action');
			console.log(url);
			let params = $(this).serialize(); // loginId=aaaa&password=aaaa
			console.log(params);
			
			$.post(url, params)  // request
			.done(function(data) { // response
				if (data.code == 1) { // 성공
					location.href = '/post/post_list_view'; // 글 목록으로 이동
				} else { // 실패
					alert(data.errorMessage);
				}
			});
			
		});
	});
</script>