package vn.com.recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.FollowCompanyRepository;

@Service
public class FollowCompanyServiceImpl implements FollowCompanyService {
	
	@Autowired
	private FollowCompanyRepository followCompanyRepository;

}
