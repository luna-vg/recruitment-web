package vn.com.recruitment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.SaveJobRepository;

@Service
public class SaveJobServiceImpl implements SaveJobService {
	
	@Autowired
	private SaveJobRepository saveJobRepository;

}
