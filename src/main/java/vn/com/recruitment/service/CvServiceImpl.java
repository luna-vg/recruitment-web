package vn.com.recruitment.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.CvRepository;
import vn.com.recruitment.entities.Cv;

@Service
public class CvServiceImpl implements CvService {
	
	@Autowired
	private CvRepository cvRepository;
	
	@Override
	public Cv findById(int id) {
		
		Optional<Cv> result = cvRepository.findById(id);
		
		Cv theCv = null;
		
		if (result.isPresent()) {
			theCv = result.get();
		}
		else {
			// we didn't find the user
			throw new RuntimeException("Did not find cv id - " + id);
		}
		
		return theCv;
		
	}
	
	@Override
	public void saveCv(Cv cv) {
		cvRepository.save(cv);
	}
	
	@Override
	public void deleteCv(Cv cv) {
		cvRepository.delete(cv);
	}

}
