package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/** 슬래시랑 **입력하고 엔터 누르면 됨
	 * 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */

	@RequestMapping("/is_duplicated_id") // get, post 둘 다 되면 사용
	public Map<String, Object> isDuplicationId(
			@RequestParam("loginId") String loginId) {
		
		Map<String, Object> result = new HashMap<>();
		boolean isDuplicated = userBO.existLoginId(loginId);
		if (isDuplicated) { // 중복일 때
			result.put("code", 1);
			result.put("result", true);
		} else { // 사용 가능
			result.put("code", 1);
			result.put("result", false);
		}
		
		return result;
	}
	
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId")  String loginId,
			@RequestParam("password")  String password,
			@RequestParam("name")  String name,
			@RequestParam("email")  String email) {
		
		// 비밀번호 암호화(hashing) - mb5(보안이 취약하기 때문에 다른거 하는게 나음)
		// aaaa => sdfadfafdadfasfwrwef (aaaa가 만들어지면 만들어진 해싱이랑 똑같이 만들어짐)
		// EncryptUtils는 static으로 되어있기 때문에 new를 하지 않고 사용할 수 있다.
		String hashedPassword = EncryptUtils.md5(password);
		
		// db insert
		userBO.addUser(loginId, hashedPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("result", "성공");
		
		return result;
	}
}
