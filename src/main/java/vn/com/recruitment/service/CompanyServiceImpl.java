package vn.com.recruitment.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.recruitment.dao.CompanyRepository;
import vn.com.recruitment.entities.Company;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public Company findById(int id) {
		Optional<Company> result = companyRepository.findById(id);
		
		Company theCompany = null;
		
		if (result.isPresent()) {
			theCompany = result.get();
		}
		else {
			return new Company();
		}
		
		return theCompany;

	}
	
	@Override
	public List<Company> getMostPopularCompanies() {
		
		List<Company> theCompanies = new ArrayList<>();
		
		List<Integer> mostPopularCompanies = companyRepository.mostPopularCompanies();
		
		for (int i : mostPopularCompanies) {
			theCompanies.add(findById(i));
		}
		
		return theCompanies;
	}
	
	@Override
	public Company findByUserId(int userId) {
		
		Optional<Integer> result = companyRepository.findCompanyIdByUserId(userId);
		
		Company theCompany = null;
		
		if (result.isPresent()) {
			theCompany = findById(result.get());
		}
		else {
			// return an empty Company
			return new Company();
		}
		
		return theCompany;

	}
	
	@Override
	public void saveCompany(Company theCompany) {
		
		companyRepository.save(theCompany);
		
	}

}
