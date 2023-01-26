package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	// private Logger logger = LoggerFactory.getLogger(PostBO.class); slf4j로 선택
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired // 스프링빈이면 autowired로 불러낼 수 있다.
	private FileManagerService fileManagerService;

	// 글 추가
	public int addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		// 파일 업로드(내 컴퓨터인 서버에 일단 저장) => 경로
		String imagePath = null;
		if (file != null) {
			// 파일이 있을 때만 업로드 => 이미지 경로를 얻어냄
			imagePath = fileManagerService.saveFile(userLoginId, file);
		}
		
		// dao insert
		return postDAO.insertPost(userId, subject, content, imagePath);
	}
	
	// 글 수정
	public void updatePost(int userId, String userLoginId, int postId, String subject, String content, MultipartFile file) {
		// 기존 글을 가져온다. (이미지가 교체될 때 기존 이미지 제거를 위해)
		Post post = getPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.warn("[update post] 수정할 메모가 존재하지 않습니다. postId:{}, userId:{}", postId, userId); // 와일드카드 문법, 단서가 될 만한 파라미터들을 넣어둔다.
			return;
		}
		
		// 멀티파일이 비어있지 않다면 업로드 후 imagePath -> 업로드가 성공하면 기존 이미지 제거
		String imagePath = null;
		if (file != null) {
			// 업로드
			imagePath = fileManagerService.saveFile(userLoginId, file);
			
			// 업로드 성공하면 기존 이미지 제거 => 업로드가 실패할 수 있으므로 업로드가 성공한 후 제거
			// imagePath가 널이 아니고, 기존 글에 이미지 패스가 널이 아닐 경우
			if (imagePath != null && post.getImagePath() != null) {
				// 이미지 제거
				fileManagerService.deleteFile(post.getImagePath());  // 무엇을 삭제하려고 하는지 알아야한다. imagePath를 삭제하면 안된다.
			}
		}
		
		// db update
		postDAO.updatePostByPostIdUserId(postId, userId, subject, content, imagePath);
	}
	
	// 글 삭제
	public int deletePostByPostIdUserId(int postId, int userId) {
		// 기존글 가져오기
		Post post = getPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.warn("[글 삭제] post is null. postId:{}, userId:{}", postId, userId);
			return 0;
		}
		
		// 업로드 되었던 이미지가 있으면 파일 삭제
		if (post.getImagePath() != null) {
			fileManagerService.deleteFile(post.getImagePath());
		}
		
		// DB delete
		return postDAO.deletePostByPostIdUserId(postId, userId);
	}
	
	public List<Post> getPostListByUserId(int userId) {
		return postDAO.getPostListByUserId(userId);
	}
	
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postDAO.selectPostByPostIdUserId(postId, userId);
	}
}
