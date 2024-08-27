package vn.com.recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.ApplyPostRepository;
import vn.com.recruitment.entities.ApplyPost;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {
	
	@Autowired
	private ApplyPostRepository applyPostRepository;
	
	@Override
	public void saveApplyPost(ApplyPost theApplyPost) {
		
		applyPostRepository.save(theApplyPost);
		
	}
		
}
