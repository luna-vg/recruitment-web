package vn.com.recruitment.service;

import java.util.List;

import vn.com.recruitment.entities.Recruitment;

public interface RecruitmentService {

	Recruitment findById(int id);

	List<Recruitment> getMostCommonRecruitments();

	void deleteRecruitment(int id);

	void saveRecruitment(Recruitment theRecuitment);

	List<Recruitment> findRecruitmentsByKeySearch(String keySearch);

	List<Recruitment> findRecruitmentsByAddressKeySearch(String keySearch);

	List<Recruitment> findRecruitmentsByCompanyKeySearch(String keySearch);

}
