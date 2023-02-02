package com.memo.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

import ch.qos.logback.classic.Logger;

@SpringBootTest
class UserRestControllerTest {

	@Autowired // @SpringBootTest가 있어서 가능
	UserBO userBO; // test에서는 private 안붙여도됨
	
	// src/main/클래스 > new > JUnit Test Case 테스트 파일 생성
	//@Test
	void test() {
		User user = userBO.getUserByLoginIdPassword("aaaa", "74b8733745420d4d33f80c4663dc5e5");
		assertNotNull(user); // null이 아님을 확인해주는 메소드
	}
	
	//@Transactional    // rollback, 실제로 insert 일어났으며 입력된 값도 삭제됨(이걸 꼭 추가하자), 로직 상에서 많이 사용됨
	//@Test
	void 유저추가테스트() {
		userBO.addUser("cccc", "cccc", "cccc", "cccc"); // 에러가 없으면 DB에 쌓인다. 그리고 롤백시키기
	}
	
	

}
