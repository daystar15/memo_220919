package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// 스프링빈으로 할지 자바빈으로 할지 선택
@Component // bo, dao, controller에 속하지 않을 때 쓰는 어노테이션, 제일 부모격임, 일반적인 스프링빈
public class FileManagerService {

	// 실제 이미지가 저장될 경로(서버)
	// static final 같이 있으면 상수가 되고 대문자로 쓴다. 주소 마지막에 꼭 슬래시를 넣는다★★
	public static final String FILE_UPLOAD_PATH = "D:\\kimnamhui\\6_spring_project\\memo\\workspace\\.metadata\\images/";
	
	// input: MultipartFile, userLoginId
	// output: image path
	public String saveFile(String userLoginId, MultipartFile file) {
		// 파일 디렉토리 - 사람마다 폴더를 저장함(userLoginId를 받아온 이유)
		// 예) aaaa_162090932/sun.png(시간을 유일한 폴더값으로 만든다)
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";  // aaaa_162090932/
		String filePath = FILE_UPLOAD_PATH + directoryName; //D:\\kimnamhui\\6_spring_project\\memo\\workspace\\.metadata\\images/aaaa_162090932/
		
		File directory = new File(filePath); // 폴더 만들어낼 준비
		if (directory.mkdir() == false) {  // 폴더가 만들어짐 boolean
			return null;  // 폴더 만드는데 실패 시 이미지패스 null
		}
		
		// 파일 업로드: byte 단위로 업로드 된다.
		// byte[] bytes = file.getBytes(); try-catch로 하기
		// 확장자를 분해해서 영어문자로 올라갈 수 있게 만들어야함(개인 플젝 때, 한글 안올라가는 문제 해결위해)
		try {
			byte[] bytes = file.getBytes();
			// Path import는 nio로 임포트하기
			Path path = Paths.get(filePath + file.getOriginalFilename()); // originalFilename은 사용자가 올린 파일명
			Files.write(path, bytes); // 업로드하는 순간
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드 성공했으면 이미지 url path를 리턴한다.
		// http://localhost:8080/images/aaaa_162090932/sun.png WebMvcConfig에서 웹이미지와 매핑함
		return "/images/" + directoryName + file.getOriginalFilename(); // 한글로 바뀌면 오리지널네임도 바뀐다.
	}
}
