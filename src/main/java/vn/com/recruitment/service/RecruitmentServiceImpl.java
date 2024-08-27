package vn.com.recruitment.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.ApplyPostRepository;
import vn.com.recruitment.dao.RecruitmentRepository;
import vn.com.recruitment.entities.Recruitment;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {
	
	@Autowired
	private RecruitmentRepository recruitmentRepository;
	
	@Autowired
	private ApplyPostRepository applyPostRepository;
	
	@Override
	public Recruitment findById(int id) {
		Optional<Recruitment> result = recruitmentRepository.findById(id);
		
		Recruitment theRecruitment = null;
		
		if (result.isPresent()) {
			theRecruitment = result.get();
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find recruitment id - " + id);
		}
		
		return theRecruitment;

	}
	
	@Override
	public void saveRecruitment(Recruitment theRecruitment) {
		recruitmentRepository.save(theRecruitment);
	}
	
	@Override
	public void deleteRecruitment(int id) {
		recruitmentRepository.deleteById(id);
	}
	
	@Override
	public List<Recruitment> getMostCommonRecruitments() {
		
		List<Recruitment> theRecruitments = new ArrayList<>();
		
		List<Integer> commonRecruitments = applyPostRepository.mostCommonRecruitments();
		
		for (int i : commonRecruitments) {
			theRecruitments.add(findById(i));
		}
		
		return theRecruitments;
		
	}
	
	@Override
	public List<Recruitment> findRecruitmentsByKeySearch(String keySearch) {
		
		List<Recruitment> theRecruitments = new ArrayList<>();
		
		keySearch = keySearch.toUpperCase();
		
		List<Integer> recruitmentsByKeySearch = recruitmentRepository.recruitmentsByTitleKeySearch(keySearch);
		
		for (int i : recruitmentsByKeySearch) {
			theRecruitments.add(findById(i));
		}
		
		return theRecruitments;

	}

	@Override
	public List<Recruitment> findRecruitmentsByAddressKeySearch(String keySearch) {
		
		List<Recruitment> theRecruitments = new ArrayList<>();
		
		keySearch = keySearch.toUpperCase();
		
		List<Integer> recruitmentsByKeySearch = recruitmentRepository.recruitmentsByAddressKeySearch(keySearch);
		
		for (int i : recruitmentsByKeySearch) {
			theRecruitments.add(findById(i));
		}
		
		return theRecruitments;
		
	}

	@Override
	public List<Recruitment> findRecruitmentsByCompanyKeySearch(String keySearch) {
		
		List<Recruitment> theRecruitments = new ArrayList<>();
		
		keySearch = keySearch.toUpperCase();
		
		List<Integer> recruitmentsByKeySearch = recruitmentRepository.recruitmentsByCompanyKeySearch(keySearch);
		
		for (int i : recruitmentsByKeySearch) {
			theRecruitments.add(findById(i));
		}
		
		return theRecruitments;
		
	}
	
}
