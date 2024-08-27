package vn.com.recruitment.service;

import java.util.List;

import vn.com.recruitment.entities.Company;

public interface CompanyService {

	Company findById(int id);

	List<Company> getMostPopularCompanies();

	Company findByUserId(int userId);

	void saveCompany(Company theCompany);

}
