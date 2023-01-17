<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요.">
		<textarea class="form-control" rows="15" id="content" placeholder="내용을 입력해주세요"></textarea>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg,.jpeg,.png,.gif"> 
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두 지우기</button>
				<button type="button" id="postCreateBtn" class="btn btn-info">저장</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		// 목록 버튼 클릭 => 글 목록
		$('#postListBtn').on('click', function() {
			location.href = "/post/post_list_view";
		});
		
		// 모두 지우기 버튼 클릭 => 제목, 글 내용 지운다
		$('#clearBtn').on('click', function() {
			$('#subject').val(""); // 빈칸으로 채워짐
			$('#content').val(""); // 빈칸으로 세팅
		});
		
		// 글 저장
		$('#postCreateBtn').on('click', function() {
			let subject = $('#subject').val().trim();
			let content = $('#content').val();
			
			if (subject == '') {
				alert('제목을 입력하세요');
				return;
			}
			
			console.log(content);
			
			let file = $('#file').val(); // 파일명(경로)
			// alert(file);
			
			// 파일이 업로드 된 경우에만 확장자 체크
			if(file != '') {
				//alert(file.split(".").pop().toLowerCase()); // 맨 마지막에 쌓여져있는 배열칸을 뺀다. 확장자를 소문자로 변경
				let ext = file.split(".").pop().toLowerCase();
				if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1) { // 배열안에 포함되지 않았을 때
					alert("이미지 파일만 업로드 할 수 있습니다.");
					$('#file').val(""); // 파일을 비운다.
					return;
				}
			}
			
			// 서버 - ajax
			// 이미지를 업로드 할 때는 form태그가 있어야함.(js에서 만든다)
			// append로 넣는 값은 폼태그의 name으로 넣는 것과 같다(request parameter)
			let formData = new FormData(); // 껍데기
			
			// name 속성을 이용하여 만든다
			formData.append("subject", subject);  // "subject"는 리퀘스트 파라미터명
			formData.append("content", content);
			formData.append("file", $('#file')[0].files[0]);
			
			// ajax 통신으로 formData에 있는 데이터 전송
			$.ajax({
				// request
				type:"post"
				, url:"/post/create"
				, data:formData  // 폼객체를 통째로, requestBody에 명확히 담겨져야하는 설정, String이 아니다!
				, enctype:"multipart/form-data" // 파일 업로드를 위한 필수 설정
				, processData:false  // 파일 업로드를 위한 필수 설정
				, contentType:false
				
				// response
				, success:function(data) {
					if (data.code == 1) {
						// 성공
						alert("메모가 저장되었습니다.");
						location.href = "/post/post_list_view";
					} else {
						// 실패(성공은 했지만 로직상 실패)
						alert(data.errorMessage);
					}
				}
				, error:function(e) { // ajax가 완전 실패
					alert("메모 저장에 실패했습니다.");
				}
			});
			
		});
	});
</script>