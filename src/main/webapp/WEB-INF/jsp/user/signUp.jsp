<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
	<h1 class="font-weight-bold mb-4">회원 가입</h1>
	<form id="signUpForm" method="post" action="/user/sign_up">
		<table class="signUp table">
			<tr>
				<th class="font-weight-bold">* 아이디</th>
				<td>
					<div class="d-flex">
						<input type="text" class="form-control mr-2" name="loginId" id="loginId">
						<button type="button" class="btn btn-success" id="idBtn">중복확인</button>
					</div>
					<%-- 아이디 체크 결과 --%>
						<%-- d-none 클래스: display none (보이지 않게) --%>
					<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
					<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
					<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
				</td>
			</tr>
			<tr>
				<th class="font-weight-bold">* 비밀번호</th>
				<td>
					<input type="password" class="form-control" name="password" id="password">
				</td>
			</tr>
			<tr>
				<th class="font-weight-bold">* 비밀번호 확인</th>
				<td>
					<input type="password" class="form-control" name="confirmPassword" id="confirmPassword">
				</td>
			</tr>
			<tr>
				<th class="font-weight-bold">* 이름</th>
				<td>
					<input type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요">
				</td>
			</tr>
			<tr>
				<th class="font-weight-bold">* 이메일 주소</th>
				<td>
					<input type="text" class="form-control" name="email" id="email" placeholder="이메일 주소를 입력하세요">
				</td>
			</tr>
		</table>
		<button type="submit" id="signUpBtn" class="btn btn-primary float-right">회원가입</button>
	</form>
</div>

<script>
	$(document).ready(function() {
		// 중복확인 버튼
		$("#idBtn").on("click", function() {
			// 초기화
			$("#idCheckLength").addClass('d-none');  // 숨김
			$("#idCheckDuplicated").addClass('d-none'); // 숨김
			$("#idCheckOk").addClass('d-none'); // 숨김
			
			let loginId = $("#loginId").val().trim();
			
			if (loginId.length < 4) {
				$("#idCheckLength").removeClass('d-none');  // 경고문구 노출
				return; //서버에 가면 안되기 때문에 반드시 리턴을 해서 종료해야함
			}
			
			// AJAX 통신 - 중복확인
			$.ajax({
				// request 
				// type 안쓰면 자동으로 get형식으로 됨
				url:"/user/is_duplicated_id"
				, data:{"loginId":loginId}
				
				// response
				, success:function(data) {
					if (data.code == 1) {
						// 성공
						if (data.result) {
							// 중복
							$('#idCheckDuplicated').removeClass('d-none');
						} else {
							// 사용 가능
							$('#idCheckOk').removeClass('d-none');
						}
					} else {
						// 실패
						alert(data.errorMessage);
					}
				}
				, error:function(e) {
					alert("중복확인에 실패했습니다.");
				}
			});
		});
		
		
		
		// 회원가입
		$("#signUpForm").on('submit', function(e) {
			e.preventDefault(); // 서브밋 기능 중단
			
			// validation
			let loginId = $("#loginId").val().trim();
			if (loginId.length == '') {
				alert("아이디를 입력해주세요");
				return false; // submit이라 false를 붙여야함
			}
			
			let password = $("#password").val().trim();
			let confirmPassword = $("#confirmPassword").val().trim();
			if (password.length == '' confirmPassword.length == '') {
				alert("비밀번호를 입력해주세요");
				return false;
			}

			let name = $("#name").val().trim();
			if (name.length == '') {
				alert("이름을 입력해주세요");
				return false;
			}

			let email = $("#email").val().trim();
			if (email.length == '') {
				alert("이메일를 입력해주세요");
				return false;
			}
			
			// 아이디 중복확인 완료 됐는지 확인 -> idCheckOk d-none을 가지고 있으면 확인하라는 alert 띄워야함
			if ($("#idCheckOk").hasClass('d-none')) {
				alert("아이디 중복확인을 다시 해주세요.");
				return false; // 서버에 못가고 빠져나가게
			}
			
			// 서버로 보내는 방법
			// 1) submit
			// form 태그는 여러개가 있다고 가정을 하기 때문에 [0] 표시를 해주어야함
			// $(this)[0].submit();  // 화면이 넘어간다
			
			// 2) ajax
			let url = $(this).attr('action'); // /user/sign_up
			let params = $(this).serialize(); // form 태그에 있는 name으로 파라미터를 구성
			console.log(params);
			
			$.post(url, params) // request
			.done(function(data) { // Object로 변환되어있는 상태이다(jquery post 함수 검색)
				// response
				if (data.code == 1) {
					// 성공
					alert("가입을 환영합니다! 로그인 해주세요.");
					location.href = "/user/sign_in_view";
				} else {
					// 실패
					alert(data.errorMessage);
				}
			});
		});
	});
</script>